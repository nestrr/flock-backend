package com.nestrr.apps.flock.standing.service;

import com.nestrr.apps.flock.standing.dto.StandingDto;
import com.nestrr.apps.flock.standing.mapper.StandingMapper;
import com.nestrr.apps.flock.standing.repository.StandingRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StandingServiceImpl implements StandingService {
  private final StandingRepository standingRepository;
  private final StandingMapper standingMapper;

  public StandingServiceImpl(StandingRepository standingRepository, StandingMapper standingMapper) {
    this.standingRepository = standingRepository;
    this.standingMapper = standingMapper;
  }

  @Override
  public List<StandingDto> getAllStandings() {
    return standingRepository.findAll().stream().map(standingMapper::toStandingDto).toList();
  }

  @Override
  public StandingDto getStandingById(String id) {
    return standingRepository.findById(id).map(standingMapper::toStandingDto).orElse(null);
  }
}
