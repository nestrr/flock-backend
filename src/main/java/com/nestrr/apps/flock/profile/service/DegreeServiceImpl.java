package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.degreetype.service.DegreeTypeService;
import com.nestrr.apps.flock.profile.entity.DegreeView;
import com.nestrr.apps.flock.profile.mapper.DegreeViewMapper;
import com.nestrr.apps.flock.profile.repository.DegreeViewRepository;
import com.nestrr.apps.flock.program.service.ProgramService;
import org.springframework.stereotype.Service;

@Service
public class DegreeServiceImpl implements DegreeService {
  private final DegreeViewRepository degreeViewRepository;

  public DegreeServiceImpl(
      DegreeViewRepository degreeViewRepository,
      DegreeTypeService degreeTypeService,
      ProgramService programService,
      DegreeViewMapper degreeViewMapper) {
    this.degreeViewRepository = degreeViewRepository;
  }

  @Override
  public DegreeView getDegreeByTypeAndProgramCodes(String degreeCode, String programCode) {
    DegreeView degree =
        degreeViewRepository.findByTypeAndProgramCodes(degreeCode, programCode).orElse(null);
    return degree;
  }
}
