package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.entity.DegreeView;

public interface DegreeService {
  DegreeView getDegreeByTypeAndProgramCodes(String degreeCode, String programCode);
}
