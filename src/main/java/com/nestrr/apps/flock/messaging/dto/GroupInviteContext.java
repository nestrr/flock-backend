package com.nestrr.apps.flock.messaging.dto;

import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.profile.entity.Person;
import lombok.Builder;

@Builder
public record GroupInviteContext(Group group, Person admin) {}
