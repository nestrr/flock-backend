package com.nestrr.apps.flock.program.service;

import com.nestrr.apps.flock.program.dto.ProgramDto;
import java.util.List;

public interface ProgramService {

  List<ProgramDto> getAllPrograms();

  ProgramDto getProgramByCode(String code);

  List<ProgramDto> getProgramsByDegreeTypeCode(String degreeTypeCode);
}
