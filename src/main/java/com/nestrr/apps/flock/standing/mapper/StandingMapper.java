package com.nestrr.apps.flock.standing.mapper;

import com.nestrr.apps.flock.standing.dto.StandingDto;
import com.nestrr.apps.flock.standing.entity.Standing;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StandingMapper {
  StandingDto toStandingDto(Standing standing);
}
