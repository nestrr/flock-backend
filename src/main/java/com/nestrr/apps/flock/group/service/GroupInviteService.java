package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import java.util.List;

public interface GroupInviteService {

  void createInvites(Group group, List<String> recipientIds);

  List<GroupInvite> getGroupInvites(String groupId, GroupInviteStatuses status);

  void deleteByGroupId(String groupId);
}
