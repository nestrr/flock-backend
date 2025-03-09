package com.nestrr.apps.flock.group.entity.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public record GroupMembershipId(String groupId, String personId) implements Serializable {

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj instanceof GroupMembershipId(String groupId1, String personId1)) {
      return groupId1.equals(groupId) && personId1.equals(personId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, personId);
  }
}
