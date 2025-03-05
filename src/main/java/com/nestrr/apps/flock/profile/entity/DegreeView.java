package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import net.jcip.annotations.Immutable;

@Entity
@Data
@Builder
@Immutable
@NoArgsConstructor
@AllArgsConstructor
public class DegreeView {
  @Id private String id;

  private String programCode;

  private String degreeTypeCode;

  private String programName;

  private String degreeTypeName;
}
