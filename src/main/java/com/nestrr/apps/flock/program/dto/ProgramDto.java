package com.nestrr.apps.flock.program.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProgramDto {
  private String code;
  private String name;
}
