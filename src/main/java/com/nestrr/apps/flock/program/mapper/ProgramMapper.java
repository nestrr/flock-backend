package com.nestrr.apps.flock.program.mapper;

import com.nestrr.apps.flock.program.dto.ProgramDto;
import com.nestrr.apps.flock.program.entity.Program;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProgramMapper {
  ProgramDto toProgramDto(Program program);
}
