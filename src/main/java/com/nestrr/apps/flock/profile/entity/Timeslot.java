package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.entity.id.TimeslotId;
import jakarta.persistence.*;
import java.time.LocalTime;
import lombok.*;

@Entity
@IdClass(TimeslotId.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {
  @Getter @Id private String personId;
  @Getter @Id private Integer day;

  @Getter @Version private Integer version;

  @Getter private LocalTime startTime;
  @Getter private LocalTime endTime;
  @Getter private String reliability;

  @Getter private String flexibility;
}
