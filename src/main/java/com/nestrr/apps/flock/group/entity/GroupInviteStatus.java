package com.nestrr.apps.flock.group.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public record GroupInviteStatus(
    @Id @GeneratedValue(strategy = GenerationType.UUID) String id,
    @Version Integer version,
    String name,
    String description) {

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other
        instanceof
        GroupInviteStatus(String id1, Integer version1, String name1, String description1)) {
      return id1.equals(id)
          && version1.equals(version)
          && name1.equals(name)
          && description1.equals(description);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, name, description);
  }
}
