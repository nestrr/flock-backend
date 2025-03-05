package com.nestrr.apps.flock.profile.entity.id;

import java.io.Serializable;
import java.util.Objects;

public record RoleAssignmentId(String personId, String roleId) implements Serializable {
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj instanceof RoleAssignmentId(String personId1, String roleId1)) {
      return personId1.equals(personId) && roleId1.equals(roleId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(personId, roleId);
  }
}
