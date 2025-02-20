package com.nestrr.apps.flock.standing.service;

import com.nestrr.apps.flock.standing.dto.StandingDto;
import java.util.List;

public interface StandingService {

  List<StandingDto> getAllStandings();

  StandingDto getStandingById(String id);
}
