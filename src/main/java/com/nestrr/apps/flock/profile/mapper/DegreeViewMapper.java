package com.nestrr.apps.flock.profile.mapper;

import com.nestrr.apps.flock.profile.dto.DegreeDto;
import com.nestrr.apps.flock.profile.entity.DegreeView;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DegreeViewMapper {
  DegreeDto toDegreeDto(DegreeView degree);

  DegreeView viewFromDegreeDto(DegreeDto degreeDto);
}
