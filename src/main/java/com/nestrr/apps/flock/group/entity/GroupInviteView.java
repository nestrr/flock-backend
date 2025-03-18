package com.nestrr.apps.flock.group.entity;

import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

@Entity
@Data
@Immutable
@NoArgsConstructor
@AllArgsConstructor
public final class GroupInviteView {

  @EmbeddedId private GroupInviteId id;
  private String name;
  private String email;
  private String image;
  private String status;
}
