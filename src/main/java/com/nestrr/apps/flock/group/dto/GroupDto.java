package com.nestrr.apps.flock.group.dto;

public record GroupDto(
    String id,
    String name,
    String description,
    String image,
    String adminId,
    GroupStatusDto status) {}
