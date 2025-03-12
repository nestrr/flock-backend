package com.nestrr.apps.flock.messaging.service;

import com.nestrr.apps.flock.messaging.dto.AdminChangeContext;
import com.nestrr.apps.flock.messaging.dto.GroupDeleteContext;
import com.nestrr.apps.flock.messaging.dto.GroupInviteContext;
import com.nestrr.apps.flock.messaging.dto.NewGroupMembershipContext;
import java.util.List;

public interface MessagingService {

  void sendInviteNotification(GroupInviteContext context, List<String> recipientIds);

  void sendAdminChangeNotification(AdminChangeContext context);

  void sendGroupDeleteNotification(GroupDeleteContext context);

  void sendGroupMemberWelcomeNotification(NewGroupMembershipContext context);
}
