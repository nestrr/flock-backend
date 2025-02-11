package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.entity.id.CampusChoiceRankId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Version;
import lombok.*;

@Entity
@IdClass(CampusChoiceRankId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CampusChoiceRank {
  @Id @NonNull private String personId;

  @Id @NonNull private Integer rank;

  @Version private Integer version;

  @NonNull private String choiceId;
}
