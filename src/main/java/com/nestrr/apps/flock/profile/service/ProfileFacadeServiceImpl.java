package com.nestrr.apps.flock.profile.service;

import static com.nestrr.apps.flock.util.AuthenticationUtil.getJwtId;
import static com.nestrr.apps.flock.util.AuthenticationUtil.getRoles;

import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import com.nestrr.apps.flock.profile.dto.ProfileDto;
import com.nestrr.apps.flock.profile.dto.UpdateProfileRequest;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.mapper.ProfileMapper;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileFacadeServiceImpl implements ProfileFacadeService {

  private final PersonService personService;

  private final CampusChoiceService campusChoiceService;
  private final ProfileMapper profileMapper;

  public ProfileFacadeServiceImpl(
      PersonService personService,
      CampusChoiceService campusChoiceService,
      ProfileMapper profileMapper) {
    this.personService = personService;
    this.campusChoiceService = campusChoiceService;
    this.profileMapper = profileMapper;
  }

  @Override
  @Transactional
  public ProfileDto getOrCreateProfile(Authentication a, OidcProfileRequest oidcProfileRequest) {
    String personId = getJwtId(a);
    List<String> roles = getRoles(a);
    Person person =
        personService.getOrCreatePerson(
            personId,
            oidcProfileRequest.getEmail(),
            oidcProfileRequest.getName(),
            oidcProfileRequest.getImage(),
            roles);

    return profileMapper
        .personToBaseProfileDtoBuilder(person)
        .roles(roles)
        .campusChoices(campusChoiceService.getCampusChoices(personId))
        .major("")
        .standing("")
        .build();
  }

  @Override
  @Transactional
  public void updateProfile(Authentication a, UpdateProfileRequest updateProfileRequest) {
    String id = getJwtId(a);
    if (updateProfileRequest.getName() != null
        || updateProfileRequest.getProfilePicture() == null
        || updateProfileRequest.getBio() == null) {
      personService.updatePerson(
          id,
          updateProfileRequest.getName(),
          updateProfileRequest.getProfilePicture(),
          updateProfileRequest.getBio());
    }
    if (updateProfileRequest.getCampusChoices() != null) {
      campusChoiceService.updateCampusChoices(id, updateProfileRequest.getCampusChoices());
    }
  }

  @Transactional
  public void deleteProfile(Authentication a) {
    String id = getJwtId(a);
    personService.deletePerson(id);
  }
}
