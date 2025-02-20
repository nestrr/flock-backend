package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.dto.TimeslotDto;
import java.util.List;

public interface TimeslotService {

  List<TimeslotDto> getTimeslots(String personId);

  void updateTimeslots(
      String personId, List<List<TimeslotDto>> added, List<List<TimeslotDto>> deleted);
}
