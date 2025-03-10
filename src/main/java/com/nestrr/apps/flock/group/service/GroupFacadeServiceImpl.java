package com.nestrr.apps.flock.group.service;

import static com.nestrr.apps.flock.util.AuthenticationUtil.getJwtId;

import com.nestrr.apps.flock.group.dto.NewGroupRequest;
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
    String id = getJwtId(auth);
    String groupId = groupService.createGroup(id, newGroupRequest);
    groupInviteService.createInvites(groupId, newGroupRequest.members());
  }
}
