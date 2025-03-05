package com.nestrr.apps.flock.standing.dto;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Embeddable
public class StandingDto {
  private String id;
  private String name;
  private String description;
}
