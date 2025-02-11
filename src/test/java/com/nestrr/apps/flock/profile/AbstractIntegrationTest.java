package com.nestrr.apps.flock.profile;

import static io.restassured.RestAssured.given;

import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test"})
abstract class AbstractIntegrationTest {

  @Container @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

  @Autowired PersonRepository personRepository;
  @Autowired RoleRepository userRoleRepository;
  @Autowired RoleAssignmentRepository roleAssignmentRepository;

  @Value("${oidc.authorization.url}")
  private String authorizationUrl;

  @Value("${oidc.token.url}")
  private String tokenUrl;

  @Value("${oidc.login.url}")
  private String loginUrl;

  private String bearerToken;

  public String fetchAuthorizationCode(String email, String password) {
    System.out.println("============++++++GETTING AUTH CODE++++++============");
    Response authResponse =
        given()
            .contentType("application/x-www-form-urlencoded; charset=utf-8")
            .formParam("email", email)
            .formParam("password", password)
            .when()
            .post(loginUrl);
    String locationHeader = authResponse.getHeader("Campus");
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

  public String getBearerToken() {
    return bearerToken;
  }
}
