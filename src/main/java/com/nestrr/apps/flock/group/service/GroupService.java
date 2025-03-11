package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;

public interface GroupService {
  Group createGroup(String creatorId, NewGroupRequest newGroupRequest);
}
