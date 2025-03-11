package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.NewGroupInviteRequest;
import com.nestrr.apps.flock.group.entity.Group;
import java.util.List;

public interface GroupInviteService {
  void createInvites(NewGroupInviteRequest request);

  void createInvites(Group group, List<String> recipientIds);
}
