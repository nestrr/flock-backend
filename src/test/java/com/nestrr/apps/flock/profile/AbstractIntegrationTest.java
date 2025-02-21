package com.nestrr.apps.flock.profile;

import static io.restassured.RestAssured.given;

import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import com.nimbusds.jose.shaded.gson.JsonParser;
import io.restassured.response.Response;
import java.time.Duration;
import java.util.Base64;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test"})
abstract class AbstractIntegrationTest {

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

  @Autowired PersonRepository personRepository;
  @Autowired RoleRepository userRoleRepository;
  @Autowired RoleAssignmentRepository roleAssignmentRepository;

  @Value("${oidc.authorization.url}")
  private String authorizationUrl;

  @Value("${oidc.token.url}")
  private String tokenUrl;

  @Value("${oidc.login.url}")
  private String loginUrl;

  @Getter private String bearerToken;

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
    System.out.println(
        "============++++++SIMULATING OAUTH2.0, AUTHORIZATION CODE FLOW++++++============");
    this.bearerToken = fetchBearerToken(fetchAuthorizationCode(email, password));
  }

  public String getUserId() {
    Base64.Decoder decoder = Base64.getDecoder();

    // Decode the string
    byte[] decodedBytes = decoder.decode(bearerToken.split(".")[1]);

    // Convert the byte array to a string
    String decodedToken = new String(decodedBytes);

    System.out.println("Decoded string: " + decodedToken);
    return new JsonParser().parse(decodedToken).getAsJsonObject().get("sub").getAsString();
  }
}
