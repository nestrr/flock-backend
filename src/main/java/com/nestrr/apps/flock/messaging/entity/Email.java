package com.nestrr.apps.flock.messaging.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Email {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Version private Integer version;
  private List<String> recipients;
  private final LocalDateTime timestamp = LocalDateTime.now();
  private String subject;
  @Builder.Default private String htmlBody = "";
  @Builder.Default private String textBody = "";
  private boolean success;
}
