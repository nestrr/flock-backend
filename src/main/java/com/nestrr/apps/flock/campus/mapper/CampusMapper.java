package com.nestrr.apps.flock.campus.mapper;

import com.nestrr.apps.flock.campus.dto.CampusDto;
import com.nestrr.apps.flock.campus.entity.Campus;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CampusMapper {
  CampusDto toCampusDto(Campus campus);

  Campus fromCampusDto(CampusDto campusDto);
}
