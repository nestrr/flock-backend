package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.entity.id.RoleAssignmentId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@IdClass(RoleAssignmentId.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleAssignment {
  @Getter @Id private String personId;

  @Getter @Id private String roleId;

  @Getter @Version private int version;
}
