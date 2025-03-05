package com.nestrr.apps.flock.profile.service;

import com.nestrr.apps.flock.profile.dto.TimeslotDto;
import com.nestrr.apps.flock.profile.entity.Timeslot;
import com.nestrr.apps.flock.profile.entity.id.TimeslotId;
import com.nestrr.apps.flock.profile.mapper.TimeslotMapper;
import com.nestrr.apps.flock.profile.repository.TimeslotRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TimeslotServiceImpl implements TimeslotService {
  private final TimeslotRepository timeslotRepository;
  private final TimeslotMapper timeslotMapper;

  public TimeslotServiceImpl(TimeslotRepository timeslotRepository, TimeslotMapper timeslotMapper) {
    this.timeslotRepository = timeslotRepository;
    this.timeslotMapper = timeslotMapper;
  }

  @Override
  public List<TimeslotDto> getTimeslots(String personId) {
    Optional<List<Timeslot>> timeslots = this.timeslotRepository.findByIdPersonId(personId);
    return timeslots
        .map(timeslotList -> timeslotList.stream().map(timeslotMapper::toTimeslotDto).toList())
        .orElseGet(List::of);
  }

  @Override
  public void updateTimeslots(
      String personId, List<List<TimeslotDto>> added, List<List<TimeslotDto>> deleted) {
    added = Objects.requireNonNullElse(added, List.of());
    deleted = Objects.requireNonNullElse(deleted, List.of());
    for (int day = 0; day < added.size(); day++) {
      List<TimeslotDto> timeslotsInDay = added.get(day);
      int currentDay = day;
      timeslotsInDay.stream()
          .map(t -> timeslotMapper.fromTimeslotDto(t, personId, currentDay))
          .forEach(timeslotRepository::save);
    }
    for (int day = 0; day < deleted.size(); day++) {
      List<TimeslotDto> timeslotsInDay = deleted.get(day);
      int currentDay = day;

      timeslotsInDay.stream()
          .map(t -> new TimeslotId(personId, currentDay, t.from(), t.to()))
          .forEach(timeslotRepository::deleteById);
    }
  }
}
