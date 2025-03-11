package com.nestrr.apps.flock.profile;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import com.nestrr.apps.flock.shared.AuthenticatedTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CampusControllerTest extends AuthenticatedTest {

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
  }

  @Test
  void canGetCampuses() {
    given()
        .port(getPort())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .when()
        .get("/campus")
        .then()
        .statusCode(HttpStatus.OK.value());
  }
}
