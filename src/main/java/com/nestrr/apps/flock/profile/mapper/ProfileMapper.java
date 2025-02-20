package com.nestrr.apps.flock.profile.mapper;

import com.nestrr.apps.flock.campus.dto.CampusDto;
import com.nestrr.apps.flock.profile.dto.*;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.mapper.constants.ProfileMapperConstants;
import com.nestrr.apps.flock.standing.dto.StandingDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProfileMapper {
  /**
   * Maps Person entity to ProfileDtoBuilder, <b>excluding</b> profile standing and major.
   *
   * @param person - The Person entity
   * @return ProfileDtoBuilder Builder instance with base details filled
   */
  default ProfileDto.ProfileDtoBuilder personToBaseProfileDtoBuilder(Person person) {
    return ProfileDto.builder()
        .name(person.getName())
        .email(person.getEmail())
        .image(person.getImage())
        .bio(person.getBio())
        .firstLogin(person.getLastLogin() == null);
  }

  /**
   * Maps Person entity to ProfileDto.
   *
   * @param person - The Person entity
   * @param degree - The degree details
   * @param standing - The year name (e.g. freshman)
   * @return ProfileDto complete ProfileDto
   */
  default ProfileDto toProfileDto(
      Person person,
      List<String> roles,
      StandingDto standing,
      DegreeDto degree,
      List<TimeslotDto> preferredTimesList,
      List<CampusDto> campusChoices) {
    Map<String, List<TimeslotDto>> preferredTimesMap = new HashMap<>();
    for (int i = 0; i < preferredTimesList.size(); i++) {
      TimeslotDto timeslot = preferredTimesList.get(i);
      preferredTimesMap
          .computeIfAbsent(ProfileMapperConstants.DAYS[i], k -> new ArrayList<>())
          .add(timeslot);
    }
    return ProfileDto.builder()
        .name(person.getName())
        .email(person.getEmail())
        .image(person.getImage())
        .bio(person.getBio())
        .standing(standing)
        .degree(degree)
        .roles(roles)
        .preferredTimes(preferredTimesMap)
        .campusChoices(campusChoices)
        .firstLogin(person.getLastLogin() == null)
        .build();
  }
}
