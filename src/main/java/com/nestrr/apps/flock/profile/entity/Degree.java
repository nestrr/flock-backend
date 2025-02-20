package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Degree {
  @Id private String id;

  @Version private Integer version;

  private String programCode;

  private String degreeTypeCode;
}
