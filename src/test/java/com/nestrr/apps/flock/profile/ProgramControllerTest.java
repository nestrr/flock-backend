package com.nestrr.apps.flock.profile;

import static io.restassured.RestAssured.given;

import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProgramControllerTest extends AbstractIntegrationTest {
  @LocalServerPort private int port;

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
  }

  private final OidcProfileRequest oidcProfileRequest =
      OidcProfileRequest.builder().name("Test").email("test@gmail.com").image("image").build();

  @BeforeEach
  void canCreateProfile() {
    setBearerToken(oidcProfileRequest.getEmail(), "password");
    given()
        .port(port)
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .body(oidcProfileRequest)
        .post("/profile/me")
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  @Test
  void canGetPrograms() {
    given()
        .port(port)
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .get("/program")
        .then()
        .statusCode(HttpStatus.OK.value());
  }
}
