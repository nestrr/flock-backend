package com.nestrr.apps.flock.group.service;

import static com.nestrr.apps.flock.util.AuthenticationUtil.getJwtId;

import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupFacadeServiceImpl implements GroupFacadeService {
  private final GroupService groupService;
  private final GroupInviteService groupInviteService;

  public GroupFacadeServiceImpl(GroupService groupService, GroupInviteService groupInviteService) {
    this.groupService = groupService;
    this.groupInviteService = groupInviteService;
  }

  @Override
  @Transactional
  public void createGroup(Authentication auth, NewGroupRequest newGroupRequest) {
    String creatorId = getJwtId(auth);
    Group group = groupService.createGroup(creatorId, newGroupRequest);
    groupInviteService.createInvites(group, newGroupRequest.members());
    groupMembershipService.addMember(group.getId(), creatorId);
  }
}
