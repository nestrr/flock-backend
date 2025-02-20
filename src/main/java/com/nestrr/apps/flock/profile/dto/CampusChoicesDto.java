package com.nestrr.apps.flock.profile.dto;

import com.nestrr.apps.flock.campus.dto.CampusDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CampusChoicesDto {
  private List<CampusDto> choices;
  private String personId;
}
