package com.nestrr.apps.flock.profile.service;

import java.util.List;

public interface CampusChoiceService {
  void updateCampusChoices(String personId, List<String> newChoices, List<String> deletedChoices);
}
