package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
  @Id private String id;

  @Version private Integer version;

  private String name;

  private String description;
}
