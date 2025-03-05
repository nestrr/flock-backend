package com.nestrr.apps.flock.profile.entity.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public record TimeslotId(String personId, Integer day, LocalTime startTime, LocalTime endTime)
    implements Serializable {

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj
        instanceof TimeslotId(String id, Integer day1, LocalTime startTime1, LocalTime endTime1)) {
      return id.equals(personId)
          && day1.equals(day)
          && startTime1.equals(startTime)
          && endTime1.equals(endTime);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(personId, day, startTime, endTime);
  }
}
