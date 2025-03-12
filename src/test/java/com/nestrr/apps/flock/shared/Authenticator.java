package com.nestrr.apps.flock.shared;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import com.nestrr.apps.flock.profile.repository.RoleRepository;
import io.restassured.response.Response;
import java.util.Base64;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class Authenticator {
  @Value("${oidc.authorization.url}")
  private String authorizationUrl;

  @Value("${oidc.token.url}")
  private String tokenUrl;

  @Value("${oidc.login.url}")
  private String loginUrl;

  @Autowired private PersonRepository personRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private RoleAssignmentRepository roleAssignmentRepository;
  @Autowired private ObjectMapper mapper;
  @Getter private Authenticated auth;
  private final Logger log = LoggerFactory.getLogger(Authenticator.class);

  public Authenticated of(String email, String password) {
    return new Authenticated(email, password);
  }

  public class Authenticated {

    @Getter private final String bearerToken;

    private Authenticated(String email, String password) {
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

    private String fetchAuthorizationCode(String email, String password) {
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

    private String fetchBearerToken(String authorizationCode) {
      System.out.println("============++++++GETTING TOKEN++++++============");
      Response tokenResponse =
          given()
              .contentType("application/x-www-form-urlencoded; charset=utf-8")
              .body("code=" + authorizationCode)
              .when()
              .post(tokenUrl);
      return tokenResponse.getBody().jsonPath().get("id_token");
    }
  }
}
