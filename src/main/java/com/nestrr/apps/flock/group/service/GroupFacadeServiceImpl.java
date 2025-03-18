package com.nestrr.apps.flock.group.service;

import static com.nestrr.apps.flock.util.AuthenticationUtil.getJwtId;

import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.dto.*;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.GroupMembership;
import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
import com.nestrr.apps.flock.messaging.dto.*;
import com.nestrr.apps.flock.messaging.service.MessagingService;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.service.PersonService;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupFacadeServiceImpl implements GroupFacadeService {
  private final GroupService groupService;
  private final GroupInviteService groupInviteService;
  private final GroupMembershipService groupMembershipService;
  private final PersonService personService;
  private final MessagingService messagingService;

  public GroupFacadeServiceImpl(
      GroupService groupService,
      GroupInviteService groupInviteService,
      GroupMembershipService groupMembershipService,
      PersonService personService,
      MessagingService messagingService) {
    this.groupService = groupService;
    this.groupInviteService = groupInviteService;
    this.groupMembershipService = groupMembershipService;
    this.personService = personService;
    this.messagingService = messagingService;
  }

  @Override
  @Transactional
  public void createGroup(Authentication auth, NewGroupRequest newGroupRequest) {
    String creatorId = getJwtId(auth);
    Group group = groupService.createGroup(creatorId, newGroupRequest);
    groupInviteService.createInvites(group, newGroupRequest.members());
    groupMembershipService.addMember(group.getId(), creatorId);
    sendInvites(group, newGroupRequest.members());
  }

  @Override
  @Transactional
  public void inviteUsers(Authentication auth, String groupId, List<String> memberIds) {
    if (memberIds.size() == 1 && memberIds.getFirst().equals(getJwtId(auth))) return;
    Group group = groupService.findGroupById(groupId).orElseThrow(NoSuchElementException::new);
    groupInviteService.createInvites(group, memberIds);
    sendInvites(group, memberIds);
  }

  @Override
  @Transactional
  public void deleteInvite(Authentication auth, String groupId, String memberId) {
    groupInviteService.deleteByGroupAndPersonId(groupId, memberId);
  }

  @Override
  @Transactional
  public List<GroupInviteDto> getInvites(
      Authentication auth, String groupId, Optional<String> status) {
    if (status.isPresent()) return groupInviteService.getGroupInviteViews(groupId, status.get());
    return groupInviteService.getGroupInviteViews(groupId);
  }

  @Transactional
  @Override
  public List<GroupMembershipDto> getMembers(String groupId) {
    return groupMembershipService.getMembershipViewsByGroupId(groupId);
  }

  @Transactional
  @Override
  public void removeMember(Authentication auth, String groupId, String memberId) {
    String removerId = getJwtId(auth);
    if (groupService.isGroupAdmin(groupId, memberId))
      throw new IllegalArgumentException(
          String.format(
              "Member ID %s is attempting to leave group ID %s while being the only admin.",
              memberId, groupId));
    groupMembershipService.removeMember(groupId, memberId);
    Group group = groupService.getGroup(groupId);
    Person member = personService.getPerson(memberId);
    DeletedGroupMembershipContext context =
        DeletedGroupMembershipContext.builder()
            .group(group)
            .member(member)
            .removerId(removerId)
            .build();
    messagingService.sendGroupMemberGoodbyeNotification(context);
  }

  @Override
  @Transactional
  public void respondToInvite(
      Authentication auth, String groupId, String memberId, String statusId) {
    GroupInviteStatuses status = groupInviteService.acceptInvite(groupId, memberId, statusId);
    if (status.equals(GroupInviteStatuses.ACCEPTED)) {
      groupMembershipService.addMember(groupId, memberId);
      Group group = groupService.getGroup(groupId);
      Person member = personService.getPerson(memberId);
      NewGroupMembershipContext context =
          NewGroupMembershipContext.builder().group(group).member(member).build();
      messagingService.sendGroupMemberWelcomeNotification(context);
    }
  }

  @Override
  @Transactional
  public List<GroupDto> getSelfGroups(Authentication auth) {
    String personId = getJwtId(auth);
    return groupMembershipService.getGroupsByPersonId(personId);
  }

  @Transactional
  @Override
  public List<GroupDto> getSelfGroups(Authentication auth, Optional<String> statusName) {
    String personId = getJwtId(auth);
    return statusName.isPresent()
        ? groupMembershipService.getGroupsByPersonAndStatusName(personId, statusName.get())
        : groupMembershipService.getGroupsByPersonId(personId);
  }

  @Override
  @Transactional
  public void updateGroup(String groupId, UpdateGroupRequest updateGroupRequest) {
    if (updateGroupRequest.adminId() != null) {
      Group oldGroup = groupService.getGroup(groupId);
      Person oldAdmin = personService.getPerson(oldGroup.getAdminId());
      Person newAdmin = personService.getPerson(updateGroupRequest.adminId());
      AdminChangeContext context =
          AdminChangeContext.builder()
              .group(oldGroup)
              .oldAdmin(oldAdmin)
              .newAdmin(newAdmin)
              .build(); // Use old details of group in change context, so that emails refer to the
      // well-known details of the group
      messagingService.sendAdminChangeNotification(context);
    }
    groupService.updateGroup(groupId, updateGroupRequest);
  }

  @Override
  @Transactional
  public void deleteGroup(String groupId) {
    List<GroupInvite> pendingInvites =
        groupInviteService.getGroupInvites(groupId, GroupInviteStatuses.RESPONSE_PENDING);
    if (!pendingInvites.isEmpty()) {
      groupInviteService.deleteByGroupId(groupId);
    }
    Group group = groupService.getGroup(groupId);
    List<String> memberIds =
        groupMembershipService.getMembershipsByGroupId(groupId).stream()
            .map(GroupMembership::getId)
            .map(GroupMembershipId::personId)
            .toList();
    messagingService.sendGroupDeleteNotification(
        GroupDeleteContext.builder().group(group).memberIds(memberIds).build());
    groupService.deleteGroup(groupId);
  }

  private void sendInvites(Group group, List<String> recipientIds) throws NoSuchElementException {
    Person admin = personService.getPerson(group.getAdminId());
    GroupInviteContext context = GroupInviteContext.builder().group(group).admin(admin).build();
    messagingService.sendInviteNotification(context, recipientIds);
  }
}
