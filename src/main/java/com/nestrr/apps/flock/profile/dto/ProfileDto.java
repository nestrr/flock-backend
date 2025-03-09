package com.nestrr.apps.flock.profile.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfileDto {
  private String id;
  private Boolean newAccount;
  private String name;
  private String email;
  private String image;
  private String bio;
  private StandingDto standing;
  private DegreeDto degree;
  private List<String> roles;
  private List<CampusDto> campusChoices;
  private Map<String, List<TimeslotDto>> timeslots;
}
