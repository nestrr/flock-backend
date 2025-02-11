package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import com.nestrr.apps.flock.profile.dto.ProfileDto;
import com.nestrr.apps.flock.profile.dto.UpdateProfileRequest;
import org.springframework.security.core.Authentication;

public interface ProfileFacadeService {

  ProfileDto getOrCreateProfile(Authentication a, OidcProfileRequest oidcProfileRequest);

  void updateProfile(Authentication a, UpdateProfileRequest updateProfileRequest);

  void deleteProfile(Authentication a);
}
