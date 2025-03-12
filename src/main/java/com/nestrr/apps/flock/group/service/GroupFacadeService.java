package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import java.util.*;
import org.springframework.security.core.Authentication;

public interface GroupFacadeService {
  void createGroup(Authentication auth, NewGroupRequest newGroupRequest);

  List<GroupDto> getSelfGroups(Authentication auth);

  void updateGroup(String groupId, UpdateGroupRequest updateGroupRequest);

  void deleteGroup(String groupId);

  void inviteUsers(Authentication auth, String groupId, List<String> memberIds);

  void removeMember(String groupId, String memberId);

  void respondToInvite(Authentication auth, String groupId, String memberId, String statusId);
}
