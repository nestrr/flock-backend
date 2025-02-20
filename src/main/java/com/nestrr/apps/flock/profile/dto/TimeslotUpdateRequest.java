package com.nestrr.apps.flock.profile.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TimeslotUpdateRequest {
  private List<List<TimeslotDto>> added;
  private List<List<TimeslotDto>> deleted;
}
