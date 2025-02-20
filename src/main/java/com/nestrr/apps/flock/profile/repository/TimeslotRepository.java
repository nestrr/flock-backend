package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Timeslot;
import com.nestrr.apps.flock.profile.entity.id.TimeslotId;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface TimeslotRepository
    extends ListPagingAndSortingRepository<Timeslot, TimeslotId>,
        ListCrudRepository<Timeslot, TimeslotId> {
  Optional<Timeslot> findByDay(Integer day);

  @Query(nativeQuery = true, value = "SELECT * FROM timeslot WHERE person_id=?1")
  Optional<List<Timeslot>> findByPersonId(String personId);

  /** Any time after this start time <b>exclusive</b>. */
  @Query(nativeQuery = true, value = "SELECT * FROM timeslot WHERE start_time>=?1")
  Optional<Timeslot> findByStartTimePost(LocalTime startTime);

  /** Any time before this end time, <b>exclusive</b>. */
  @Query(nativeQuery = true, value = "SELECT * FROM timeslot WHERE end_time<=?1")
  Optional<Timeslot> findByEndTimePre(LocalTime endTime);
}
