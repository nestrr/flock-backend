package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.degreetype.dto.DegreeTypeDto;
import com.nestrr.apps.flock.degreetype.service.DegreeTypeService;
import com.nestrr.apps.flock.profile.dto.DegreeDto;
import com.nestrr.apps.flock.profile.entity.Degree;
import com.nestrr.apps.flock.profile.mapper.DegreeMapper;
import com.nestrr.apps.flock.profile.repository.DegreeRepository;
import com.nestrr.apps.flock.program.dto.ProgramDto;
import com.nestrr.apps.flock.program.service.ProgramService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DegreeFacadeServiceImpl implements DegreeFacadeService {
  private final DegreeRepository degreeRepository;
  private final DegreeTypeService degreeTypeService;
  private final ProgramService programService;
  private final DegreeMapper degreeMapper;

  public DegreeFacadeServiceImpl(
      DegreeRepository degreeRepository,
      DegreeTypeService degreeTypeService,
      ProgramService programService,
      DegreeMapper degreeMapper) {
    this.degreeRepository = degreeRepository;
    this.degreeTypeService = degreeTypeService;
    this.programService = programService;
    this.degreeMapper = degreeMapper;
  }

  @Override
  public DegreeDto getDegreeById(String degreeId) {
    Degree degree = degreeRepository.findById(degreeId).orElse(null);
    if (degree == null) return null;
    ProgramDto program = programService.getProgramByCode(degree.getProgramCode());
    DegreeTypeDto degreeType = degreeTypeService.getDegreeTypeByCode(degree.getDegreeTypeCode());
    return degreeMapper.toDegreeDto(degree, program, degreeType);
  }

  @Override
  public Degree getDegreeByTypeAndProgramCodes(String degreeCode, String programCode) {
    Optional<Degree> degree = degreeRepository.findByTypeAndProgramCodes(degreeCode, programCode);
    return degree.orElseThrow(() -> new NullPointerException("Degree does not exist."));
  }
}
