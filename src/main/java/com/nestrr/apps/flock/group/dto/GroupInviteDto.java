package com.nestrr.apps.flock.group.dto;


public record GroupInviteDto(
    String personId, String groupId, String email, String name, String image, String status) {}
