package com.nestrr.apps.flock.profile.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CampusDto {
  private String id;
  private String name;
  private String description;
}
