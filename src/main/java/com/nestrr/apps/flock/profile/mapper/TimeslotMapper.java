package com.nestrr.apps.flock.profile.mapper;

import com.nestrr.apps.flock.profile.dto.TimeslotDto;
import com.nestrr.apps.flock.profile.entity.Timeslot;
import com.nestrr.apps.flock.profile.entity.TimeslotView;
import com.nestrr.apps.flock.profile.entity.id.TimeslotId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TimeslotMapper {
  @Mapping(source = "id.startTime", target = "from")
  @Mapping(source = "id.endTime", target = "to")
  @Mapping(source = "id.day", target = "day")
  TimeslotDto toTimeslotDto(Timeslot timeslot);

  @Mapping(source = "startTime", target = "from")
  @Mapping(source = "endTime", target = "to")
  TimeslotDto toTimeslotDto(TimeslotView timeslotView);

  default Timeslot fromTimeslotDto(TimeslotDto timeslotDto, String personId, Integer day) {
    return Timeslot.builder()
        .id(new TimeslotId(personId, day, timeslotDto.from(), timeslotDto.to()))
        .reliability(timeslotDto.reliability())
        .flexibility(timeslotDto.flexibility())
        .build();
  }

  @Mapping(source = "from", target = "startTime")
  @Mapping(source = "to", target = "endTime")
  TimeslotView viewFromTimeslotDto(TimeslotDto timeslotDto);
}
