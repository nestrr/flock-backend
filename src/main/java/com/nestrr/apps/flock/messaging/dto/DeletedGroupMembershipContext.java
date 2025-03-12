package com.nestrr.apps.flock.messaging.dto;

import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.profile.entity.Person;
import lombok.Builder;

@Builder
public record DeletedGroupMembershipContext(Group group, Person member, String removerId) {}
