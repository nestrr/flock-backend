package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  @Id private String id;
  @Version private Integer version;
  private String name;
  private String email;
  private String image;
  private String bio;
  private String standingId;
  private String majorId;
  private LocalDateTime lastLogin;
}
