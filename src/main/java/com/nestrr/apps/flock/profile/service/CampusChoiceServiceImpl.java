package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.dto.CampusDto;
import com.nestrr.apps.flock.profile.entity.Campus;
import com.nestrr.apps.flock.profile.entity.CampusChoice;
import com.nestrr.apps.flock.profile.entity.CampusChoiceRank;
import com.nestrr.apps.flock.profile.mapper.CampusMapper;
import com.nestrr.apps.flock.profile.repository.CampusChoiceRankRepository;
import com.nestrr.apps.flock.profile.repository.CampusChoiceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CampusChoiceServiceImpl implements CampusChoiceService {

  private final CampusChoiceRankRepository campusChoiceRankRepository;
  private final CampusChoiceRepository campusChoiceRepository;
  private final CampusMapper campusMapper;

  public CampusChoiceServiceImpl(
      CampusChoiceRankRepository campusChoiceRankRepository,
      CampusChoiceRepository campusChoiceRepository,
      CampusMapper campusMapper) {
    this.campusChoiceRankRepository = campusChoiceRankRepository;
    this.campusMapper = campusMapper;
    this.campusChoiceRepository = campusChoiceRepository;
  }

  @Override
  public List<CampusDto> getCampusChoices(String personId) {
    Optional<List<Campus>> campuses =
        campusChoiceRankRepository.getRankedCampusesByPersonId(personId);
    return campuses
        .map(campusList -> campusList.stream().map(campusMapper::toCampusDto).toList())
        .orElseGet(ArrayList::new);
  }

  @Override
  public void updateCampusChoices(String personId, List<String> newChoices) {
    List<CampusChoice> oldCampusChoices =
        campusChoiceRepository.findByPersonId(personId).orElseGet(ArrayList::new);
    int choiceIdx = 0;
    for (String campusId : newChoices) {
      CampusChoice saved = campusChoiceRepository.save(new CampusChoice(personId, campusId));
      campusChoiceRankRepository.save(new CampusChoiceRank(personId, choiceIdx++, saved.getId()));
    }

    if (!oldCampusChoices.isEmpty()) {
      List<String> oldChosenCampusIds =
          oldCampusChoices.stream()
              .map(CampusChoice::getId)
              .filter(id -> !newChoices.contains(id))
              .toList();
      deleteCampusChoices(personId, oldChosenCampusIds);
    }
  }

  public void deleteCampusChoices(String personId, List<String> toDelete) {
    toDelete.forEach(
        campusId -> campusChoiceRepository.deleteByPersonIdAndCampusId(personId, campusId));
  }
}
