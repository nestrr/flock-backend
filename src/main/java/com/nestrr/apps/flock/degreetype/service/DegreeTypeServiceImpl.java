package com.nestrr.apps.flock.degreetype.service;

import com.nestrr.apps.flock.degreetype.dto.DegreeTypeDto;
import com.nestrr.apps.flock.degreetype.mapper.DegreeTypeMapper;
import com.nestrr.apps.flock.degreetype.repository.DegreeTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DegreeTypeServiceImpl implements DegreeTypeService {
  private final DegreeTypeRepository degreeTypeRepository;
  private final DegreeTypeMapper degreeTypeMapper;

  public DegreeTypeServiceImpl(
      DegreeTypeRepository degreeTypeRepository, DegreeTypeMapper degreeTypeMapper) {
    this.degreeTypeRepository = degreeTypeRepository;
    this.degreeTypeMapper = degreeTypeMapper;
  }

  @Override
  public List<DegreeTypeDto> getAllDegreeTypes() {
    return this.degreeTypeRepository.findAll().stream()
        .map(degreeTypeMapper::toDegreeTypeDto)
        .toList();
  }

  @Override
  public DegreeTypeDto getDegreeTypeByCode(String code) {
    return degreeTypeRepository
        .findByCode(code)
        .map(degreeTypeMapper::toDegreeTypeDto)
        .orElse(null);
  }
}
