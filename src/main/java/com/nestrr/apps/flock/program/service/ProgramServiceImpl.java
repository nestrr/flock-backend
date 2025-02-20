package com.nestrr.apps.flock.program.service;

import com.nestrr.apps.flock.program.dto.ProgramDto;
import com.nestrr.apps.flock.program.entity.Program;
import com.nestrr.apps.flock.program.mapper.ProgramMapper;
import com.nestrr.apps.flock.program.repository.ProgramRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProgramServiceImpl implements ProgramService {
  private final ProgramRepository programRepository;
  private final ProgramMapper programMapper;

  public ProgramServiceImpl(ProgramRepository programRepository, ProgramMapper programMapper) {
    this.programRepository = programRepository;
    this.programMapper = programMapper;
  }

  @Override
  public List<ProgramDto> getAllPrograms() {
    return programRepository.findAll().stream().map(programMapper::toProgramDto).toList();
  }

  @Override
  public ProgramDto getProgramByCode(String code) {
    return programRepository.findByCode(code).map(programMapper::toProgramDto).orElse(null);
  }

  @Override
  public List<ProgramDto> getProgramsByDegreeTypeCode(String degreeTypeCode) {
    List<Program> programs = programRepository.findByDegreeType(degreeTypeCode).orElse(List.of());
    return programs.stream().map(programMapper::toProgramDto).toList();
  }
}
