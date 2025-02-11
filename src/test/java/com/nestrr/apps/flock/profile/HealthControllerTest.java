package com.nestrr.apps.flock.profile;

import static io.restassured.RestAssured.given;

import com.nestrr.apps.flock.health.dto.HealthDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

class HealthControllerTest extends AbstractIntegrationTest {
  @LocalServerPort private int port;

  @Test
  void canGetHealthIfUnauthenticated() {

    HealthDto healthDto =
        given().port(port).contentType(ContentType.JSON).when().get("/health").as(HealthDto.class);

    Assertions.assertTrue(healthDto.isHealth());
  }
}
