package com.nestrr.apps.flock.group.entity;

import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
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
public final class GroupMembershipView {

  @EmbeddedId private GroupMembershipId id;
  private String name;
  private String email;
  private String image;
}
