package com.nestrr.apps.flock.degreetype.mapper;

import com.nestrr.apps.flock.degreetype.dto.DegreeTypeDto;
import com.nestrr.apps.flock.degreetype.entity.DegreeType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DegreeTypeMapper {
  DegreeTypeDto toDegreeTypeDto(DegreeType degreeType);
}
