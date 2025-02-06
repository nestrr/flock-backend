package com.nestrr.apps.flock.profile.entity.id;

import java.io.Serializable;

public record CampusChoiceRankId(String personId, Integer rank) implements Serializable {
  public boolean equals(CampusChoiceRankId one, CampusChoiceRankId two) {
    return one.personId().equals(two.personId()) && one.rank().equals(two.rank());
  }
}
