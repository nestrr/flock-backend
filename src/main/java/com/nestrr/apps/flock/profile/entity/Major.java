package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Major {
  @Id private String id;

  @Version private Integer version;

  private String name;

  private String description;
}
