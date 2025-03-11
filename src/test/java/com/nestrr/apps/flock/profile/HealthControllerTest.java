package com.nestrr.apps.flock.profile;

import static io.restassured.RestAssured.given;

import com.nestrr.apps.flock.health.dto.HealthDto;
import com.nestrr.apps.flock.shared.UnauthenticatedTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

class HealthControllerTest extends UnauthenticatedTest {

  @Test
  void canGetHealthIfUnauthenticated() {

    HealthDto healthDto =
        given()
            .port(getPort())
            .contentType(ContentType.JSON)
            .when()
            .get("/health")
            .as(HealthDto.class);

    Assertions.assertTrue(healthDto.isHealth());
  }
}
