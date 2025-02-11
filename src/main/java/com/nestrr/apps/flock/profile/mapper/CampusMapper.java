package com.nestrr.apps.flock.profile.mapper;

import com.nestrr.apps.flock.profile.dto.CampusDto;
import com.nestrr.apps.flock.profile.entity.Campus;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CampusMapper {
  CampusDto toCampusDto(Campus campus);

  Campus fromCampusDto(CampusDto campusDto);
}
