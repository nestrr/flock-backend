package com.nestrr.apps.flock.group.dto;

import java.time.LocalDateTime;

public record GroupInviteDto(
    String groupId, String personId, LocalDateTime expiresOn, GroupInviteStatusDto status) {}
