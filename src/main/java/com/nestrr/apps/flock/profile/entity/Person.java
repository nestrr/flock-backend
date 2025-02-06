package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  @Getter @Setter @Id private String id;
  @Getter @Setter @Version private Integer version;
  @Getter @Setter private String name;
  @Getter @Setter private String email;
  @Getter @Setter private String image;
  @Getter @Setter private String bio;
  @Getter @Setter private String standingId;
  @Getter @Setter private String majorId;
}
