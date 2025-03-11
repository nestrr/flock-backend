package com.nestrr.apps.flock.shared;


import java.time.Duration;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class UnauthenticatedTest implements AbstractIntegrationTest {

  @Getter @LocalServerPort private int port;

  @Container
  @ServiceConnection(type = JdbcConnectionDetails.class)
  public static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:17").withMinimumRunningDuration(Duration.ofSeconds(5L));

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }
}
