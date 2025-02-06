package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Major {
  @Getter @Id private String id;

  @Getter @Version private Integer version;

  @Getter private String name;

  @Getter private String description;
}
