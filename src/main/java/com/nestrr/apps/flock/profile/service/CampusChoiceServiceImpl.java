package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.campus.dto.CampusDto;
import com.nestrr.apps.flock.campus.entity.Campus;
import com.nestrr.apps.flock.campus.mapper.CampusMapper;
import com.nestrr.apps.flock.profile.entity.CampusChoice;
import com.nestrr.apps.flock.profile.entity.CampusChoiceRank;
import com.nestrr.apps.flock.profile.repository.CampusChoiceRankRepository;
import com.nestrr.apps.flock.profile.repository.CampusChoiceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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
  public void updateCampusChoices(
      String personId, List<String> newChoices, List<String> deletedChoices) {
    deletedChoices.forEach(
        campusId -> campusChoiceRepository.deleteByPersonIdAndCampusId(personId, campusId));
    AtomicInteger rankNumber = new AtomicInteger();
    newChoices.forEach(
        campusId -> {
          CampusChoice saved = campusChoiceRepository.save(new CampusChoice(personId, campusId));
          campusChoiceRankRepository.save(
              new CampusChoiceRank(personId, rankNumber.getAndIncrement(), saved.getId()));
        });
  }
}
