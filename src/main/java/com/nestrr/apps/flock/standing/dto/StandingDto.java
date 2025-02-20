package com.nestrr.apps.flock.standing.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StandingDto {
  private String id;
  private String name;
  private String description;
}
