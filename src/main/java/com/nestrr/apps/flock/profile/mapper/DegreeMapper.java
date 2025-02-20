package com.nestrr.apps.flock.profile.mapper;

import com.nestrr.apps.flock.degreetype.dto.DegreeTypeDto;
import com.nestrr.apps.flock.profile.dto.DegreeDto;
import com.nestrr.apps.flock.profile.entity.Degree;
import com.nestrr.apps.flock.program.dto.ProgramDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DegreeMapper {
  default DegreeDto toDegreeDto(Degree degree, ProgramDto program, DegreeTypeDto degreeType) {
    return DegreeDto.builder()
        .id(degree.getId())
        .programCode(program.getCode())
        .programName(program.getName())
        .degreeTypeCode(degreeType.getCode())
        .degreeTypeName(degreeType.getName())
        .build();
  }
}
