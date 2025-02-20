package com.nestrr.apps.flock.degreetype.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DegreeType {
  @Id private String id;

  @Version private Integer version;

  private String code;
  private String name;
}
