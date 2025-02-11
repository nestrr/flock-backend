package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import com.nestrr.apps.flock.profile.dto.ProfileDto;
import org.springframework.security.core.Authentication;

public interface ProfileFacadeService {

  ProfileDto getOrCreateProfile(Authentication a, OidcProfileRequest oidcProfileRequest);
}
