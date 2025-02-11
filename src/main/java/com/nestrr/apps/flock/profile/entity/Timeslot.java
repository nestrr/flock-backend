package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.entity.id.TimeslotId;
import jakarta.persistence.*;
import java.time.LocalTime;
import lombok.*;

@Entity
@IdClass(TimeslotId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {
  @Id private String personId;
  @Id private Integer day;

  @Version private Integer version;

  private LocalTime startTime;
  private LocalTime endTime;
  private String reliability;

  private String flexibility;
}
