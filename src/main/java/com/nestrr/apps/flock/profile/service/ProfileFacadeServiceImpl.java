package com.nestrr.apps.flock.profile.service;

import static com.nestrr.apps.flock.util.AuthenticationUtil.getJwtId;
import static com.nestrr.apps.flock.util.AuthenticationUtil.getRoles;

import com.nestrr.apps.flock.profile.dto.*;
import com.nestrr.apps.flock.profile.entity.Degree;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.mapper.ProfileMapper;
import com.nestrr.apps.flock.standing.dto.StandingDto;
import com.nestrr.apps.flock.standing.service.StandingService;
import io.micrometer.common.util.StringUtils;
import java.sql.SQLDataException;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileFacadeServiceImpl implements ProfileFacadeService {

  private final PersonService personService;
  private final TimeslotService timeslotService;
  private final RoleAssignmentService roleAssignmentService;
  private final CampusChoiceService campusChoiceService;
  private final StandingService standingService;
  private final DegreeFacadeService degreeFacadeService;
  private final ProfileMapper profileMapper;

  public ProfileFacadeServiceImpl(
      PersonService personService,
      CampusChoiceService campusChoiceService,
      RoleAssignmentService roleAssignmentService,
      TimeslotService timeslotService,
      StandingService standingService,
      DegreeFacadeService degreeFacadeService,
      ProfileMapper profileMapper) {
    this.personService = personService;
    this.roleAssignmentService = roleAssignmentService;
    this.campusChoiceService = campusChoiceService;
    this.timeslotService = timeslotService;
    this.standingService = standingService;
    this.degreeFacadeService = degreeFacadeService;
    this.profileMapper = profileMapper;
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
  public void updateProfile(Authentication a, ProfileUpdateRequest profileUpdateRequest) {
    String personId = getJwtId(a);
    String degreeId = null;
    if (!StringUtils.isBlank(profileUpdateRequest.getDegreeTypeCode())
        && !StringUtils.isBlank(profileUpdateRequest.getProgramCode())) {
      Degree degree =
          degreeFacadeService.getDegreeByTypeAndProgramCodes(
              profileUpdateRequest.getDegreeTypeCode(), profileUpdateRequest.getProgramCode());
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
    if (profileUpdateRequest.getPreferredTimes() != null)
      timeslotService.updateTimeslots(
          personId,
          profileUpdateRequest.getPreferredTimes().getAdded(),
          profileUpdateRequest.getPreferredTimes().getDeleted());
  }

  @Override
  public ProfileDto getProfile(Authentication a) {
    String personId = getJwtId(a);
    List<String> roles = getRoles(a);
    try {
      Person person = personService.getPerson(personId);
      StandingDto standing =
          person.getStandingId() == null
              ? null
              : standingService.getStandingById(person.getStandingId());
      DegreeDto degree =
          person.getDegreeId() == null
              ? null
              : degreeFacadeService.getDegreeById(person.getDegreeId());
      List<TimeslotDto> preferredTimes = timeslotService.getTimeslots(personId);
      return profileMapper.toProfileDto(
          person,
          roles,
          standing,
          degree,
          preferredTimes,
          campusChoiceService.getCampusChoices(personId));
    } catch (SQLDataException e) {
      return null;
    }
  }
}
