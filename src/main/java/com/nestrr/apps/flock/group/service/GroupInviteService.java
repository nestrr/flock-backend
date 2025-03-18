package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.dto.GroupInviteDto;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.GroupInviteView;
import java.util.List;

public interface GroupInviteService {

  void createInvites(Group group, List<String> recipientIds);

  List<GroupInviteDto> getGroupInviteViews(String groupId, String status);

  List<GroupInviteDto> getGroupInviteViews(String groupId);

  List<GroupInvite> getGroupInvites(String groupId, GroupInviteStatuses status);

  void deleteByGroupId(String groupId);

  GroupInviteStatuses acceptInvite(String groupId, String memberId, String statusId);

  void deleteByGroupAndPersonId(String groupId, String memberId);
}
