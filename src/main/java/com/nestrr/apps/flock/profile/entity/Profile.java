package com.nestrr.apps.flock.profile.entity;

import com.nestrr.apps.flock.profile.dto.CampusDto;
import com.nestrr.apps.flock.profile.dto.DegreeDto;
import com.nestrr.apps.flock.profile.dto.StandingDto;
import com.nestrr.apps.flock.profile.dto.TimeslotDto;
import com.nestrr.apps.flock.profile.entity.converter.CampusDtoAttributeConverter;
import com.nestrr.apps.flock.profile.entity.converter.DegreeDtoAttributeConverter;
import com.nestrr.apps.flock.profile.entity.converter.TimeslotDtoAttributeConverter;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class Profile {
  @Id
  @Column(insertable = false, updatable = false)
  private String id;

  @Column(insertable = false, updatable = false)
  private String name;

  @Column(insertable = false, updatable = false)
  private String email;

  @Column(insertable = false, updatable = false)
  private String image;

  @Column(insertable = false, updatable = false)
  private String bio;

  private List<String> roles;

  @JdbcTypeCode(SqlTypes.JSON)
  private StandingDto standing;

  @Convert(converter = DegreeDtoAttributeConverter.class)
  private DegreeDto degree;

  @Convert(converter = TimeslotDtoAttributeConverter.class)
  private List<TimeslotDto> timeslots;

  @Convert(converter = CampusDtoAttributeConverter.class)
  private List<CampusDto> campusChoices;
}
