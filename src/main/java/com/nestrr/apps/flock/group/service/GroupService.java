package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;

public interface GroupService {
  Group createGroup(String creatorId, NewGroupRequest newGroupRequest);

  Group updateGroup(String groupId, UpdateGroupRequest updateGroupRequest);

  Boolean isGroupOwner(String groupId, String personId);
}
