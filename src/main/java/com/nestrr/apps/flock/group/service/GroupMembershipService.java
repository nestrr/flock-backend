package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.GroupMembershipDto;
import com.nestrr.apps.flock.group.entity.GroupMembership;
import java.util.List;

public interface GroupMembershipService {
  void addMember(String groupId, String personId);

  boolean isGroupMember(String groupId, String personId);

  void removeMember(String groupId, String personId);

  List<GroupDto> getGroupsByPersonId(String personId);

  List<GroupDto> getGroupsByPersonAndStatusName(String personId, String statusName);

  List<GroupMembership> getMembershipsByGroupId(String groupId);

  List<GroupMembershipDto> getMembershipViewsByGroupId(String groupId);
}
