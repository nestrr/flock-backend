package com.nestrr.apps.flock.group.entity;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class GroupStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Version private Integer version;
  private String name;
  private String description;

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other instanceof GroupStatus otherStatus) {
      return otherStatus.getId().equals(id)
          && otherStatus.getVersion().equals(version)
          && otherStatus.getName().equals(name)
          && otherStatus.getDescription().equals(description);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, name, description);
  }
}
