package com.nestrr.apps.flock.profile.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfileDto {

  private String name;
  private String email;
  private String image;
  private String bio;
  private String standing;
  private String major;
  private Boolean firstLogin;
  private List<String> roles;
  private List<CampusDto> campusChoices;
}
