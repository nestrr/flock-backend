package com.nestrr.apps.flock.group.entity;

import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public record GroupMembership(@EmbeddedId GroupMembershipId id, @Version Integer version) {

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other instanceof GroupMembership(GroupMembershipId id1, Integer version1)) {
      return id1.equals(id) && version1.equals(version);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version);
  }
}
