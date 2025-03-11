package com.nestrr.apps.flock.messaging.service;

import com.nestrr.apps.flock.messaging.dto.GroupInviteContext;
import java.util.List;

public interface MessagingService {

  void sendInviteNotification(GroupInviteContext context, List<String> recipientIds);
}
