package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.entity.id.TimeslotId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {
  @EmbeddedId private TimeslotId id;

  @Version private Integer version;

  private Integer reliability;

  private Integer flexibility;
}
