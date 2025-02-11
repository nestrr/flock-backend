package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CampusChoice {
  @Id private String id;

  @Version private int version;

  @NonNull private String personId;

  @NonNull private String campusId;
}
