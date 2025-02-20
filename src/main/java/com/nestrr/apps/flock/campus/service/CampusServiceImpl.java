package com.nestrr.apps.flock.campus.service;

import com.nestrr.apps.flock.campus.dto.CampusDto;
import com.nestrr.apps.flock.campus.mapper.CampusMapper;
import com.nestrr.apps.flock.campus.repository.CampusRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CampusServiceImpl implements CampusService {
  private final CampusRepository campusRepository;
  private final CampusMapper campusMapper;

  public CampusServiceImpl(CampusRepository campusRepository, CampusMapper campusMapper) {
    this.campusRepository = campusRepository;
    this.campusMapper = campusMapper;
  }

  @Override
  public List<CampusDto> getAllCampuses() {
    return campusRepository.findAll().stream().map(campusMapper::toCampusDto).toList();
  }
}
