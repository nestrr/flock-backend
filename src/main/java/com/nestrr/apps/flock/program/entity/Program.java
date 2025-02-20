package com.nestrr.apps.flock.program.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program {
  @Id private String id;

  @Version private Integer version;
  private String code;
  private String name;
}
