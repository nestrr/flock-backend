package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Campus;
import com.nestrr.apps.flock.profile.entity.CampusChoiceRank;
import com.nestrr.apps.flock.profile.entity.id.CampusChoiceRankId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CampusChoiceRankRepository
    extends ListPagingAndSortingRepository<CampusChoiceRank, CampusChoiceRankId>,
        CrudRepository<CampusChoiceRank, CampusChoiceRankId> {

  Optional<List<CampusChoiceRank>> findByPersonId(String personId);

  // We do not delete a campus choice rank. We delete the choice, and the rank gets deleted on
  // cascade.
  @Query(
      nativeQuery = true,
      value =
          "SELECT ccr.person_id FROM campus_choice_rank ccr JOIN campus_choice cc ON ccr.choice_id = cc.choice_id WHERE cc.campus_id=?1 AND ccr.rank=?2")
  Optional<List<String>> findPeopleByRank(String campusId, Integer rank);

  @Query(
      nativeQuery = true,
      value =
          "SELECT cmp.*"
              + "FROM campus_choice_rank ccr "
              + "JOIN campus_choice cc ON ccr.choice_id = cc.id "
              + "JOIN campus cmp ON cc.campus_id = cmp.id "
              + "WHERE ccr.person_id=?1 "
              + "ORDER BY ccr.rank")
  Optional<List<Campus>> getRankedCampusesByPersonId(String personId);
}
