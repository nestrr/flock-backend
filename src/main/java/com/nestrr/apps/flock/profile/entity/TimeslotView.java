package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.entity.id.TimeslotViewId;
import jakarta.persistence.*;
import java.time.LocalTime;
import lombok.*;
import net.jcip.annotations.Immutable;

@Entity
@Data
@Builder
@Immutable
@NoArgsConstructor
@IdClass(TimeslotViewId.class)
@AllArgsConstructor
public class TimeslotView {
  @Id private Integer day;
  @Id private LocalTime startTime;
  @Id private LocalTime endTime;

  private Integer flexibility;

  private Integer reliability;
}
