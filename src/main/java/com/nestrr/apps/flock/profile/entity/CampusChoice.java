package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampusChoice {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Version private int version;

  private String personId;

  private String campusId;
}
