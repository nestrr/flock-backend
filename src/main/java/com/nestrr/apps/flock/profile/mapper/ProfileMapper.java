package com.nestrr.apps.flock.profile.mapper;

import com.nestrr.apps.flock.profile.dto.CampusDto;
import com.nestrr.apps.flock.profile.dto.ProfileDto;
import com.nestrr.apps.flock.profile.entity.Person;
import java.util.List;
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
   * @param major - The major name
   * @param standing - The year name (e.g. freshman)
   * @return ProfileDto complete ProfileDto
   */
  default ProfileDto personToProfileDto(
      Person person,
      List<String> roles,
      String standing,
      String major,
      List<CampusDto> campusChoices) {
    return ProfileDto.builder()
        .name(person.getName())
        .email(person.getEmail())
        .image(person.getImage())
        .bio(person.getBio())
        .standing(standing)
        .major(major)
        .roles(roles)
        .campusChoices(campusChoices)
        .firstLogin(person.getLastLogin() == null)
        .build();
  }
}
