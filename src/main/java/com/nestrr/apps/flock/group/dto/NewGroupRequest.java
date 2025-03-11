package com.nestrr.apps.flock.group.dto;

import java.util.List;

public record NewGroupRequest(
    List<String> members, String name, String description, String image) {}
