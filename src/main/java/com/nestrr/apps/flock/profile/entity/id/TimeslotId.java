package com.nestrr.apps.flock.profile.entity.id;

import java.io.Serializable;

public record TimeslotId(String personId, Integer day) implements Serializable {
  public boolean equals(TimeslotId one, TimeslotId two) {
    return one.personId().equals(two.personId()) && one.day().equals(two.day());
  }
}
