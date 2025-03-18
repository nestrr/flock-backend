package com.nestrr.apps.flock.group.dto;

import lombok.Builder;

@Builder
public record GroupDto(
    String id,
    String name,
    String description,
    String image,
    String adminId,
    GroupStatusDto status) {}
