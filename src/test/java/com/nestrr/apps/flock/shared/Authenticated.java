package com.nestrr.apps.flock.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import lombok.Getter;

public class Authenticated {
  @Getter private final String bearerToken;
  private final ObjectMapper mapper = new ObjectMapper();

  public Authenticated(String token) {
    this.bearerToken = token;
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
}
