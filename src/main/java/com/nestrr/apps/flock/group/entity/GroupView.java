package com.nestrr.apps.flock.group.entity;

import com.nestrr.apps.flock.group.dto.GroupStatusDto;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.Immutable;

@Entity
@Data
@Immutable
@NoArgsConstructor
@AllArgsConstructor
public final class GroupView {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;
  private String description;
  private String image;
  private String adminId;

  @JdbcTypeCode(SqlTypes.JSON)
  private GroupStatusDto status;

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other instanceof GroupView otherGroupView) {
      return otherGroupView.getId().equals(id)
          && otherGroupView.getName().equals(name)
          && otherGroupView.getDescription().equals(description)
          && otherGroupView.getImage().equals((image))
          && otherGroupView.getAdminId().equals(adminId)
          && otherGroupView.getStatus().equals(status);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, image, adminId, status);
  }
}
