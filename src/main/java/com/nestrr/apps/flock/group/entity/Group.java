package com.nestrr.apps.flock.group.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public record Group(
    @Id @GeneratedValue(strategy = GenerationType.UUID) String id,
    @Version Integer version,
    String name,
    String description,
    String image,
    String adminId,
    String statusId) {

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other
        instanceof
        Group(
            String id1,
            Integer version1,
            String name1,
            String description1,
            String image1,
            String adminId1,
            String statusId1)) {
      return id1.equals(id)
          && version1.equals(version)
          && name1.equals(name)
          && description1.equals(description)
          && image1.equals((image))
          && adminId1.equals(adminId)
          && statusId1.equals(statusId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, name, description, image, adminId, statusId);
  }
}
