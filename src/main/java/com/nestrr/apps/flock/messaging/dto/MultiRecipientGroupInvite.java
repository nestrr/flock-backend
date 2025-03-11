package com.nestrr.apps.flock.messaging.dto;

import java.util.List;

public record MultiRecipientGroupInvite(GroupInviteContext context, List<String> recipientIds) {}
