package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.campus.dto.CampusDto;
import java.util.List;

public interface CampusChoiceService {
  List<CampusDto> getCampusChoices(String personId);

  void updateCampusChoices(String personId, List<String> newChoices, List<String> deletedChoices);
}
