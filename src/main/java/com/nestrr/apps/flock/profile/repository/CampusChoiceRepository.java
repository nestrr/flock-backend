package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.CampusChoice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CampusChoiceRepository
    extends ListPagingAndSortingRepository<CampusChoice, String>,
        CrudRepository<CampusChoice, String> {

  Optional<List<CampusChoice>> findByPersonId(String personId);

  @Modifying
  @Query(
      nativeQuery = true,
      value = "DELETE FROM campus_choice cc WHERE cc.person_id=?1 AND cc.campus_id=?2")
  void deleteByPersonIdAndCampusId(String personId, String campusId);

  @Query(
      nativeQuery = true,
      value =
          "SELECT cc.campus_id FROM campus_choice cc JOIN campus_choice_rank ccr ON ccr.choice_id = cc.choice_id WHERE ccr.rank=?2")
  Optional<List<String>> findCampusByRank(Integer rank);
}
