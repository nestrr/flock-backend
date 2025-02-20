package com.nestrr.apps.flock.profile.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CampusChoicesUpdateRequest {
  private List<String> added;
  private List<String> deleted;
}
