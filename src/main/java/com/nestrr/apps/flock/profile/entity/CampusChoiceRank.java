package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.entity.id.CampusChoiceRankId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Version;
import lombok.*;

@Entity
@IdClass(CampusChoiceRankId.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampusChoiceRank {
  @Getter @Setter @Id private String personId;

  @Getter @Setter @Id private Integer rank;

  @Getter @Setter @Version private Integer version;

  @Getter @Setter private String choiceId;
}
