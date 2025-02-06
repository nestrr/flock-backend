package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reliability {
  @Getter @Id private String id;

  @Getter @Version private Integer version;

  @Getter private Integer code;

  @Getter private String description;
}
