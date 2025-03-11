package com.nestrr.apps.flock.group.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jcip.annotations.Immutable;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GroupInviteStatus {
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
    if (other instanceof GroupInviteStatus otherGroupInviteStatus) {
      return otherGroupInviteStatus.id.equals(id)
          && otherGroupInviteStatus.version.equals(version)
          && otherGroupInviteStatus.name.equals(name)
          && otherGroupInviteStatus.description.equals(description);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, name, description);
  }
}
