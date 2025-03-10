package com.nestrr.apps.flock.group.entity;

import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class GroupInvite {
  @EmbeddedId private GroupInviteId id;
  @Version private Integer version;
  private String statusId;
  private LocalDateTime expiresOn;

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other instanceof GroupInvite otherGroupInvite) {
      return otherGroupInvite.getId().equals(id)
          && otherGroupInvite.getVersion().equals(version)
          && otherGroupInvite.getStatusId().equals((statusId))
          && otherGroupInvite.getExpiresOn().isEqual(expiresOn);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, statusId, expiresOn);
  }
}
