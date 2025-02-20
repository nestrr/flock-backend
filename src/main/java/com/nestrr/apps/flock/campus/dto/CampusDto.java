package com.nestrr.apps.flock.campus.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CampusDto {
  private String id;
  private String name;
  private String description;
}
