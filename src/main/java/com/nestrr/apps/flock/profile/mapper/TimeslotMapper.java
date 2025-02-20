package com.nestrr.apps.flock.profile.mapper;

import com.nestrr.apps.flock.profile.dto.TimeslotDto;
import com.nestrr.apps.flock.profile.entity.Timeslot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TimeslotMapper {
  @Mapping(source = "startTime", target = "from")
  @Mapping(source = "endTime", target = "to")
  TimeslotDto toTimeslotDto(Timeslot timeslot);

  @Mapping(source = "from", target = "startTime")
  @Mapping(source = "to", target = "endTime")
  default Timeslot fromTimeslotDto(TimeslotDto timeslotDto, String personId, Integer day) {
    return Timeslot.builder()
        .personId(personId)
        .day(day)
        .startTime(timeslotDto.getFrom())
        .endTime(timeslotDto.getTo())
        .reliability(timeslotDto.getReliability())
        .flexibility(timeslotDto.getFlexibility())
        .build();
  }
}
