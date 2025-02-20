package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.dto.DegreeDto;
import com.nestrr.apps.flock.profile.entity.Degree;

public interface DegreeFacadeService {

  DegreeDto getDegreeById(String degreeId);

  Degree getDegreeByTypeAndProgramCodes(String degreeCode, String programCode);
}
