package com.nestrr.apps.flock.group.entity;

import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public record GroupInvite(
    @EmbeddedId GroupInviteId id,
    @Version Integer version,
    String statusId,
    LocalDateTime expiresOn) {

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (other
        instanceof
        GroupInvite(
            GroupInviteId id1,
            Integer version1,
            String statusId1,
            LocalDateTime expiresOn1)) {
      return id1.equals(id)
          && version1.equals(version)
          && statusId1.equals((statusId))
          && expiresOn1.isEqual(expiresOn);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, statusId, expiresOn);
  }
}
