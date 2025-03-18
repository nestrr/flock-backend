package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.*;
import java.util.*;
import org.springframework.security.core.Authentication;

public interface GroupFacadeService {
  void createGroup(Authentication auth, NewGroupRequest newGroupRequest);

  List<GroupDto> getSelfGroups(Authentication auth);

  List<GroupDto> getSelfGroups(Authentication auth, Optional<String> statusId);

  void updateGroup(String groupId, UpdateGroupRequest updateGroupRequest);

  void deleteGroup(String groupId);

  void inviteUsers(Authentication auth, String groupId, List<String> memberIds);

  void deleteInvite(Authentication auth, String groupId, String memberId);

  List<GroupInviteDto> getInvites(Authentication auth, String groupId, Optional<String> status);

  List<GroupMembershipDto> getMembers(String groupId);

  void removeMember(Authentication auth, String groupId, String memberId);

  void respondToInvite(Authentication auth, String groupId, String memberId, String statusId);
}
