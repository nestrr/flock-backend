package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.NewGroupRequest;

public interface GroupService {
  String createGroup(String creatorId, NewGroupRequest newGroupRequest);
}
