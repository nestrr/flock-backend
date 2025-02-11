package com.nestrr.apps.flock.profile.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CampusChoicesDto {
  private List<CampusDto> choices;
  private String personId;
}
