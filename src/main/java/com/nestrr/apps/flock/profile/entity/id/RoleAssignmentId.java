package com.nestrr.apps.flock.profile.entity.id;

import java.io.Serializable;

public record RoleAssignmentId(String personId, String roleId) implements Serializable {
  public boolean equals(RoleAssignmentId one, RoleAssignmentId two) {
    return one.roleId().equals(two.roleId()) && one.personId().equals(two.personId());
  }
}
