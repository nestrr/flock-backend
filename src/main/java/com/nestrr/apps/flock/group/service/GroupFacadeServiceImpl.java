package com.nestrr.apps.flock.group.service;

import static com.nestrr.apps.flock.util.AuthenticationUtil.getJwtId;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
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
  public List<GroupDto> getSelfGroups(Authentication auth) {
    String personId = getJwtId(auth);
    return groupMembershipService.getGroupsByPersonId(personId);
  }

  @Override
  public Boolean isGroupOwner(String groupId, String personId) {
    return groupService.isGroupOwner(groupId, personId);
  }

  @Override
  public Group updateGroup(String groupId, UpdateGroupRequest updateGroupRequest) {
    return groupService.updateGroup(groupId, updateGroupRequest);
  }
}
