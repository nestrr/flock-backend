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
@Table(name = "\"group\"")
@NoArgsConstructor
@AllArgsConstructor
public final class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Version private Integer version;
  private String name;
  private String description;
  private String image;
  private String adminId;
  private String statusId;

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other instanceof Group otherGroup) {
      return otherGroup.getId().equals(id)
          && otherGroup.getVersion().equals(version)
          && otherGroup.getName().equals(name)
          && otherGroup.getDescription().equals(description)
          && otherGroup.getImage().equals((image))
          && otherGroup.getAdminId().equals(adminId)
          && otherGroup.getStatusId().equals(statusId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, name, description, image, adminId, statusId);
  }
}
