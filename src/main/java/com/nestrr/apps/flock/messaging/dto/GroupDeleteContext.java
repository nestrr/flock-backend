package com.nestrr.apps.flock.messaging.dto;

import com.nestrr.apps.flock.group.entity.Group;
import java.util.List;
import lombok.Builder;

@Builder
public record GroupDeleteContext(Group group, List<String> memberIds) {}
