package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampusChoice {
  @Getter @Id private String id;

  @Getter @Version private int version;

  @Getter private String personId;

  @Getter private String campusId;
}
