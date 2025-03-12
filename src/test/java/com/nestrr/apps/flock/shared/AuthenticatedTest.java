package com.nestrr.apps.flock.shared;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import io.restassured.http.ContentType;
import java.time.Duration;
import lombok.Getter;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AuthenticatedTest implements AbstractIntegrationTest {

  @Getter @LocalServerPort private int port;

  @Value("${oidc.authorization.url}")
  private String authorizationUrl;

  @Value("${oidc.token.url}")
  private String tokenUrl;

  @Value("${oidc.login.url}")
  private String loginUrl;

  private final OidcProfileRequest OIDC_PROFILE_REQUEST =
      OidcProfileRequest.builder().name("Test").email("test@gmail.com").image("image").build();

  private Authenticator.Authenticated auth;
  @Autowired private PersonRepository personRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private RoleAssignmentRepository roleAssignmentRepository;
  @Autowired private ObjectMapper mapper;
  @Getter @Autowired private Authenticator authenticator;

  private final Logger log = LoggerFactory.getLogger(AuthenticatedTest.class);

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

  public String getUserId() {
    return auth == null ? null : auth.getUserId();
  }

  public String getBearerToken() {
    return auth == null ? null : auth.getBearerToken();
  }

  @BeforeEach
  void canCreateProfile() {
    this.auth = authenticator.of(OIDC_PROFILE_REQUEST.getEmail(), "password");
    given()
        .port(port)
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .body(OIDC_PROFILE_REQUEST)
        .post("/profile/me")
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }
}
