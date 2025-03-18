package com.nestrr.apps.flock.group.dto;

public record GroupMembershipDto(
    String personId, String groupId, String email, String name, String image) {}
