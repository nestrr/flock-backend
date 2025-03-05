package com.nestrr.apps.flock.profile.mapper;

import com.nestrr.apps.flock.profile.dto.*;
import com.nestrr.apps.flock.profile.entity.Profile;
import com.nestrr.apps.flock.profile.mapper.constants.ProfileMapperConstants;
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
   * Maps Person entity to ProfileDto.
   *
   * @param profile The Profile entity
   * @return complete ProfileDto
   */
  default ProfileDto toProfileDto(Profile profile) {

    List<TimeslotDto> timeslots = profile.getTimeslots();
    if (timeslots != null) {
      Map<String, List<TimeslotDto>> timeslotsMap = new HashMap<>();
      for (int i = 0; i < timeslots.size(); i++) {
        TimeslotDto timeslot = timeslots.get(i);
        timeslotsMap
            .computeIfAbsent(ProfileMapperConstants.DAYS[timeslot.day()], k -> new ArrayList<>())
            .add(timeslot);
      }
      return ProfileDto.builder()
          .name(profile.getName())
          .email(profile.getEmail())
          .image(profile.getImage())
          .bio(profile.getBio())
          .standing(profile.getStanding())
          .degree(profile.getDegree())
          .roles(profile.getRoles())
          .timeslots(timeslotsMap)
          .campusChoices(profile.getCampusChoices())
          //        .firstLogin(profile.getLastLogin() == null)
          .build();
    }
    return ProfileDto.builder()
        .name(profile.getName())
        .email(profile.getEmail())
        .image(profile.getImage())
        .bio(profile.getBio())
        .standing(profile.getStanding())
        .degree(profile.getDegree())
        .roles(profile.getRoles())
        .campusChoices(profile.getCampusChoices())
        //        .firstLogin(profile.getLastLogin() == null)
        .build();
  }
}
