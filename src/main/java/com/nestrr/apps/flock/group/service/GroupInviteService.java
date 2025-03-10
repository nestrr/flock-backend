package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.NewGroupInviteRequest;
import java.util.List;

public interface GroupInviteService {
  void createInvites(NewGroupInviteRequest request);

  void createInvites(String groupId, List<String> recipientIds);
}
