package com.nestrr.apps.flock.profile.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfileUpdateRequest {
  private String bio;
  private String image;
  private String standingId;
  private String programCode;
  private String degreeTypeCode;
  private CampusChoicesUpdateRequest campusChoices;
  private TimeslotUpdateRequest preferredTimes;
}
