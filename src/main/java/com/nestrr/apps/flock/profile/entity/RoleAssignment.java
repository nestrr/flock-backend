package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.entity.id.RoleAssignmentId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Version;
import lombok.*;

@Entity
@Data
@IdClass(RoleAssignmentId.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleAssignment {
  @Id private String personId;

  @Id private String roleId;

  @Version private int version;
}
