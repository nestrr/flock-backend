package com.nestrr.apps.flock.profile;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import com.nestrr.apps.flock.profile.dto.ProfileDto;
import com.nestrr.apps.flock.profile.entity.Person;
import io.restassured.http.ContentType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfileControllerTest extends AbstractIntegrationTest {
  @LocalServerPort private int port;

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
  }

  @Test
  @Order(0)
  void canCreateProfile() {
    OidcProfileRequest oidcProfileRequest =
        OidcProfileRequest.builder().name("Test").email("test@gmail.com").image("image").build();
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
  @Order(1)
  void canGetProfile() {
    OidcProfileRequest oidcProfileRequest =
        OidcProfileRequest.builder().name("Test").email("test@gmail.com").image("image").build();
    setBearerToken(oidcProfileRequest.getEmail(), "password");

    ProfileDto expectedProfileDto =
        ProfileDto.builder()
            .name(oidcProfileRequest.getName())
            .email(oidcProfileRequest.getEmail())
            .image(oidcProfileRequest.getImage())
            .bio(null)
            .preferredTimes(Map.of())
            .firstLogin(true)
            .roles(List.of("student"))
            .campusChoices(new ArrayList<>())
            .build();

    ProfileDto profileDto =
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + getBearerToken())
            .when()
            .get("/profile/me")
            .as(ProfileDto.class);
    assertThat(profileDto, samePropertyValuesAs(expectedProfileDto));
  }

  @Test
  void cannotCreateProfileWithoutAuthorization() {
    OidcProfileRequest oidcProfileRequest =
        OidcProfileRequest.builder().name("Test").email("test@gmail.com").image("image").build();
    given()
        .port(port)
        .contentType(ContentType.JSON)
        .body(oidcProfileRequest)
        .when()
        .post("/profile/me")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  void cannotCreateProfileWithInvalidAuthorization() {
    OidcProfileRequest oidcProfileRequest =
        OidcProfileRequest.builder().name("Test").email("test@gmail.com").image("image").build();
    String fakeBearerToken = "randomxyz";
    given()
        .port(port)
        .header("Authorization", fakeBearerToken)
        .contentType(ContentType.JSON)
        .body(oidcProfileRequest)
        .when()
        .post("/profile/me")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  @Disabled
  void canUpdateBasicDetails() {
    OidcProfileRequest oidcProfileRequest =
        OidcProfileRequest.builder()
            .name("Maryam")
            .image("Original Image")
            .email("maryam@gmail.com")
            .build();
    //
    //    UpdateProfileRequest updateProfileRequest =
    //        UpdateProfileRequest.builder()
    //            .name("Maryam 2.0")
    //            .profilePicture("New Image")
    //            .bio("A descriptive shiny new bio")
    //            .build();

    setBearerToken(oidcProfileRequest.getEmail(), "password");
    given()
        .port(port)
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        .body(oidcProfileRequest)
        .when()
        .post("/profile/me");

    // Assert 204 NO CONTENT status code
    given()
        .port(port)
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + getBearerToken())
        //        .body(updateProfileRequest)
        .when()
        .patch("/profile/me")
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());

    Person person = personRepository.findByEmail(oidcProfileRequest.getEmail()).orElseThrow();
    //    assertEquals(person.getName(), updateProfileRequest.getName());
    //    assertEquals(person.getImage(), updateProfileRequest.getProfilePicture());
    //    assertEquals(person.getBio(), updateProfileRequest.getBio());
  }
}
