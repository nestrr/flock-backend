package com.nestrr.apps.flock.health.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class HealthDto {
  private boolean health;
}
