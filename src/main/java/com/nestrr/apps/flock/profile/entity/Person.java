package com.nestrr.apps.flock.profile.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

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
  private String degreeId;
  private LocalDateTime lastLogin;
}
