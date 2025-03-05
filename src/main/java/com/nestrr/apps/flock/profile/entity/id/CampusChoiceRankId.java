package com.nestrr.apps.flock.profile.entity.id;

import java.io.Serializable;
import java.util.Objects;

public record CampusChoiceRankId(String personId, Integer rank) implements Serializable {
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj instanceof CampusChoiceRankId(String personId1, Integer rank1)) {
      return personId1.equals(personId) && rank1.equals(rank);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(personId, rank);
  }
}
