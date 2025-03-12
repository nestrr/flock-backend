package com.nestrr.apps.flock.messaging.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

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
  private String sender;
  private String recipient;
  private final LocalDateTime timestamp = LocalDateTime.now();
  @NonNull private String subject;
  @Builder.Default private String htmlBody = "";
  @Builder.Default private String textBody = "";
  private boolean success;
}
