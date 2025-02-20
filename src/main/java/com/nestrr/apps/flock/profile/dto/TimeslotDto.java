package com.nestrr.apps.flock.profile.dto;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TimeslotDto {
  private String id;
  private LocalTime from;
  private LocalTime to;
  private Integer reliability;
  private Integer flexibility;
}
