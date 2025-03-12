package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface GroupService {
  Optional<Group> findGroupById(String groupId);

  Group createGroup(String creatorId, NewGroupRequest newGroupRequest);

  void updateGroup(String groupId, UpdateGroupRequest updateGroupRequest);

  Boolean isGroupOwner(String groupId, String personId);

  void deleteGroup(String groupId);

  Group getGroup(String groupId) throws NoSuchElementException;
}
