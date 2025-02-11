package com.nestrr.apps.flock.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
public class OidcProfileRequest {
  @Getter private String name;
  @Getter private String email;
  @Getter private String image;
}
