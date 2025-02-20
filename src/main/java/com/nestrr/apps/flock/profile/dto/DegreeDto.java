package com.nestrr.apps.flock.profile.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DegreeDto {
  private String id;
  private String programCode;
  private String programName;
  private String degreeTypeName;
  private String degreeTypeCode;
}
