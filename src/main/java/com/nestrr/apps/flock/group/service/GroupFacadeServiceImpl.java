package com.nestrr.apps.flock.group.service;

import static com.nestrr.apps.flock.util.AuthenticationUtil.getJwtId;

import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupFacadeServiceImpl implements GroupFacadeService {
  private final GroupService groupService;
  private final GroupInviteService groupInviteService;
  private final GroupMembershipService groupMembershipService;

  public GroupFacadeServiceImpl(
      GroupService groupService,
      GroupInviteService groupInviteService,
      GroupMembershipService groupMembershipService) {
    this.groupService = groupService;
    this.groupInviteService = groupInviteService;
    this.groupMembershipService = groupMembershipService;
  }

  @Override
  @Transactional
  public void createGroup(Authentication auth, NewGroupRequest newGroupRequest) {
    String creatorId = getJwtId(auth);
    Group group = groupService.createGroup(creatorId, newGroupRequest);
    groupInviteService.createInvites(group, newGroupRequest.members());
    groupMembershipService.addMember(group.getId(), creatorId);
  }

  @Override
  @Transactional
  public void inviteUsers(Authentication auth, String groupId, List<String> memberIds) {
    if (memberIds.size() == 1 && memberIds.getFirst().equals(getJwtId(auth))) return;
    Group group = groupService.findGroupById(groupId).orElseThrow(NoSuchElementException::new);
    groupInviteService.createInvites(group, memberIds);
  }

  @Override
  @Transactional
  public void respondToInvite(
      Authentication auth, String groupId, String memberId, String statusId) {
    GroupInviteStatuses status = groupInviteService.acceptInvite(groupId, memberId, statusId);
    if (status.equals(GroupInviteStatuses.ACCEPTED)) {
      groupMembershipService.addMember(groupId, memberId);
    }
  }

  @Override
  @Transactional
  public List<GroupDto> getSelfGroups(Authentication auth) {
    String personId = getJwtId(auth);
    return groupMembershipService.getGroupsByPersonId(personId);
  }

  @Override
  @Transactional
  public Boolean isGroupOwner(String groupId, String personId) {
    return groupService.isGroupOwner(groupId, personId);
  }

  @Override
  @Transactional
  public void updateGroup(String groupId, UpdateGroupRequest updateGroupRequest) {
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
    groupService.deleteGroup(groupId);
  }
}
