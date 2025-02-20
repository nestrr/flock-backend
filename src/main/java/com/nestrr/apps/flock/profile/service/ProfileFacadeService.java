package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import com.nestrr.apps.flock.profile.dto.ProfileDto;
import com.nestrr.apps.flock.profile.dto.ProfileUpdateRequest;
import org.springframework.security.core.Authentication;

public interface ProfileFacadeService {

  void storeNewProfile(Authentication a, OidcProfileRequest oidcProfileRequest);

  void updateProfile(Authentication a, ProfileUpdateRequest profileUpdateRequest);

  ProfileDto getProfile(Authentication a);
}
