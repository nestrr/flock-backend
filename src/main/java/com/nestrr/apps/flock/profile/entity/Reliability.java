package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reliability {
  @Id private String id;

  @Version private Integer version;

  private Integer code;

  private String description;
}
