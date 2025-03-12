package com.nestrr.apps.flock.group.dto;

import java.util.List;

public record UpdateGroupRequest(
    String groupId, String adminId, String name, String description, String image) {}
