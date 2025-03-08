package com.nestrr.apps.flock.profile.service;

import static com.nestrr.apps.flock.util.AuthenticationUtil.getJwtId;
import static com.nestrr.apps.flock.util.AuthenticationUtil.getRoles;

import com.nestrr.apps.flock.profile.dto.*;
import com.nestrr.apps.flock.profile.entity.DegreeView;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.entity.Profile;
import com.nestrr.apps.flock.profile.mapper.ProfileMapper;
import com.nestrr.apps.flock.profile.repository.ProfileRepository;
import com.nestrr.apps.flock.standing.service.StandingService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileFacadeServiceImpl implements ProfileFacadeService {

  private final PersonService personService;
  private final ProfileRepository profileRepository;
  private final TimeslotService timeslotService;
  private final RoleAssignmentService roleAssignmentService;
  private final CampusChoiceService campusChoiceService;
  private final StandingService standingService;
  private final DegreeService degreeService;
  private final ProfileMapper profileMapper;

  public ProfileFacadeServiceImpl(
      PersonService personService,
      CampusChoiceService campusChoiceService,
      RoleAssignmentService roleAssignmentService,
      TimeslotService timeslotService,
      StandingService standingService,
      DegreeService degreeService,
      ProfileMapper profileMapper,
      ProfileRepository profileRepository) {
    this.personService = personService;
    this.roleAssignmentService = roleAssignmentService;
    this.campusChoiceService = campusChoiceService;
    this.timeslotService = timeslotService;
    this.standingService = standingService;
    this.degreeService = degreeService;
    this.profileMapper = profileMapper;
    this.profileRepository = profileRepository;
  }

  @Override
  @Transactional
  public void storeNewProfile(Authentication a, OidcProfileRequest oidcProfileRequest) {
    String personId = getJwtId(a);
    List<String> roles = getRoles(a);
    personService.createPersonIfNeeded(
        personId,
        oidcProfileRequest.getEmail(),
        oidcProfileRequest.getName(),
        oidcProfileRequest.getImage());
    roleAssignmentService.storeRoleAssignments(personId, roles);
  }

  @Override
  @Transactional
  public void updateProfile(Authentication a, ProfileUpdateRequest profileUpdateRequest) {
    String personId = getJwtId(a);
    String degreeId = null;
    if (!StringUtils.isBlank(profileUpdateRequest.getDegreeTypeCode())
        && !StringUtils.isBlank(profileUpdateRequest.getProgramCode())) {
      DegreeView degree =
          degreeService.getDegreeByTypeAndProgramCodes(
              profileUpdateRequest.getDegreeTypeCode(), profileUpdateRequest.getProgramCode());
      if (degree == null) throw new NullPointerException("No such degree!");
      degreeId = degree.getId();
    }
    Person person =
        Person.builder()
            .id(personId)
            .image(profileUpdateRequest.getImage())
            .bio(profileUpdateRequest.getBio())
            .degreeId(degreeId)
            .standingId(profileUpdateRequest.getStandingId())
            .build();
    personService.updatePerson(person);
    if (profileUpdateRequest.getCampusChoices() != null)
      campusChoiceService.updateCampusChoices(
          personId,
          profileUpdateRequest.getCampusChoices().getAdded(),
          profileUpdateRequest.getCampusChoices().getDeleted());
    if (profileUpdateRequest.getTimeslots() != null)
      timeslotService.updateTimeslots(
          personId,
          profileUpdateRequest.getTimeslots().getAdded(),
          profileUpdateRequest.getTimeslots().getDeleted());
  }

  @Override
  public ProfileDto getProfile(Authentication a) {
    String personId = getJwtId(a);
    Profile profile = profileRepository.findById(personId).orElse(null);
    return profile == null ? null : profileMapper.toProfileDto(profile);
  }

  @Override
  public List<ProfileDto> getProfiles(Authentication a, int page, int size) {
    String personId = getJwtId(a);
    return profileRepository.findAll(PageRequest.of(page, size)).stream()
        .filter(p -> !p.getId().equals(personId))
        .map(profileMapper::toProfileDto)
        .toList();
  }

  @Transactional
  public void deleteProfile(Authentication a) {
    String id = getJwtId(a);
    personService.deletePerson(id);
  }
}
