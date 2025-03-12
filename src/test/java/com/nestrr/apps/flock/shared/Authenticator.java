package com.nestrr.apps.flock.shared;

import static io.restassured.RestAssured.given;

import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public final class Authenticator {

  @Value("${oidc.token.url}")
  private String tokenUrl;

  @Value("${oidc.login.url}")
  private String loginUrl;

  private final Logger log = LoggerFactory.getLogger(Authenticator.class);

  public Authenticated of(String email, String password) {
    String authCode = fetchAuthorizationCode(email, password);
    String token = fetchBearerToken(authCode);
    return new Authenticated(token);
  }

  private String fetchAuthorizationCode(String email, String password) {
    log.debug("============++++++GETTING AUTH CODE++++++============");
    Response authResponse =
        given()
            .contentType("application/x-www-form-urlencoded; charset=utf-8")
            .formParam("email", email)
            .formParam("password", password)
            .when()
            .post(loginUrl);
    if (authResponse.getStatusCode() != HttpStatus.OK.value())
      throw new RuntimeException(
          String.format("Failed to fetch authorization code. %s", authResponse.getStatusCode()));
    String locationHeader = authResponse.getHeader("Location");
    return locationHeader.split("code=")[1].split("&")[0];
  }

  private String fetchBearerToken(String authorizationCode) {
    log.debug("============++++++GETTING TOKEN++++++============");
    Response tokenResponse =
        given()
            .contentType("application/x-www-form-urlencoded; charset=utf-8")
            .body("code=" + authorizationCode)
            .when()
            .post(tokenUrl);
    if (tokenResponse.getStatusCode() != HttpStatus.OK.value())
      throw new RuntimeException(
          String.format("Failed to fetch token. %s", tokenResponse.getStatusCode()));

    return tokenResponse.getBody().jsonPath().get("id_token");
  }
}
