package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.campus.mapper.CampusMapper;
import com.nestrr.apps.flock.profile.entity.CampusChoice;
import com.nestrr.apps.flock.profile.entity.CampusChoiceRank;
import com.nestrr.apps.flock.profile.repository.CampusChoiceRankRepository;
import com.nestrr.apps.flock.profile.repository.CampusChoiceRepository;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class CampusChoiceServiceImpl implements CampusChoiceService {

  private final CampusChoiceRankRepository campusChoiceRankRepository;
  private final CampusChoiceRepository campusChoiceRepository;

  public CampusChoiceServiceImpl(
      CampusChoiceRankRepository campusChoiceRankRepository,
      CampusChoiceRepository campusChoiceRepository,
      CampusMapper campusMapper) {
    this.campusChoiceRankRepository = campusChoiceRankRepository;
    this.campusChoiceRepository = campusChoiceRepository;
  }

  @Override
  public void updateCampusChoices(
      String personId, List<String> newChoices, List<String> deletedChoices) {
    newChoices = Objects.requireNonNullElse(newChoices, List.of());
    deletedChoices = Objects.requireNonNullElse(deletedChoices, List.of());
    deletedChoices.forEach(
        campusId -> campusChoiceRepository.deleteByPersonIdAndCampusId(personId, campusId));
    AtomicInteger rankNumber = new AtomicInteger();
    newChoices.forEach(
        campusId -> {
          CampusChoice saved =
              campusChoiceRepository.save(
                  CampusChoice.builder().personId(personId).campusId(campusId).build());
          campusChoiceRankRepository.save(
              new CampusChoiceRank(personId, rankNumber.getAndIncrement(), saved.getId()));
        });
  }
}
