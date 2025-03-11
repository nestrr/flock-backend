package com.nestrr.apps.flock.group.entity;

import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GroupMembership {
  @EmbeddedId private GroupMembershipId id;
  @Version private Integer version;

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other instanceof GroupMembership otherGroupMembership) {
      return otherGroupMembership.getId().equals(id)
          && otherGroupMembership.getVersion().equals(version);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version);
  }
}
