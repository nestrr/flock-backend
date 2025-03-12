package com.nestrr.apps.flock.shared;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestrr.apps.flock.profile.dto.OidcProfileRequest;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.Duration;
import java.util.Base64;
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

  @Getter private String bearerToken;

  @Autowired private PersonRepository personRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private RoleAssignmentRepository roleAssignmentRepository;
  @Autowired private ObjectMapper mapper;
  private final Logger log = LoggerFactory.getLogger(AuthenticatedTest.class);
  private final OidcProfileRequest oidcProfileRequest =
      OidcProfileRequest.builder().name("Test").email("test@gmail.com").image("image").build();

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

  public String fetchAuthorizationCode(String email, String password) {
    System.out.println("============++++++GETTING AUTH CODE++++++============");
    Response authResponse =
        given()
            .contentType("application/x-www-form-urlencoded; charset=utf-8")
            .formParam("email", email)
            .formParam("password", password)
            .when()
            .post(loginUrl);
    String locationHeader = authResponse.getHeader("Location");
    return locationHeader.split("code=")[1].split("&")[0];
  }

  public String fetchBearerToken(String authorizationCode) {
    System.out.println("============++++++GETTING TOKEN++++++============");
    Response tokenResponse =
        given()
            .contentType("application/x-www-form-urlencoded; charset=utf-8")
            .body("code=" + authorizationCode)
            .when()
            .post(tokenUrl);
    return tokenResponse.getBody().jsonPath().get("id_token");
  }

  public void setBearerToken(String email, String password) {
    log.debug("============++++++SIMULATING OAUTH2.0, AUTHORIZATION CODE FLOW++++++============");
    this.bearerToken = fetchBearerToken(fetchAuthorizationCode(email, password));
  }

  public String getUserId() {
    Base64.Decoder decoder = Base64.getDecoder();

    // Decode the string
    byte[] decodedBytes = decoder.decode(bearerToken.split("\\.")[1]);

    // Convert the byte array to a string
    String decodedToken = new String(decodedBytes);
    try {
      JsonNode node = mapper.readTree(decodedToken);
      return node.get("sub").asText();
    } catch (JsonProcessingException e) {
      return "";
    }
  }

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
}
