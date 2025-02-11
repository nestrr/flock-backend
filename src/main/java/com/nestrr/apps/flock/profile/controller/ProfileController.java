package com.nestrr.apps.flock.profile.controller;

import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import com.nestrr.apps.flock.profile.dto.ProfileDto;
import com.nestrr.apps.flock.profile.service.ProfileFacadeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
  private final ProfileFacadeService profileFacadeService;

  public ProfileController(ProfileFacadeService profileFacadeService) {
    this.profileFacadeService = profileFacadeService;
  }

  @GetMapping("/health")
  public ResponseEntity<String> healthCheck(Authentication a) {
    return ResponseEntity.ok("healthy.");
  }

  @PostMapping("/me")
  public ResponseEntity<ProfileDto> getOrCreateProfile(
      Authentication a, @RequestBody @Valid OidcProfileRequest oidcProfileRequest) {
    ProfileDto profileDto = profileFacadeService.getOrCreateProfile(a, oidcProfileRequest);
    return ResponseEntity.ok(profileDto);
  }
}
