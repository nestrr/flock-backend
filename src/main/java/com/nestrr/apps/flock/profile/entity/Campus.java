package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campus {
  @Id private String id;

  @Version private int version;

  private String name;

  private String description;
}
