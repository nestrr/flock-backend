package com.nestrr.apps.flock.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {
  private final JwtAuthenticationConverter converter;

  @Value("${oidc.jwk.url}")
  private String keySetUri;

  public SecurityConfig(JwtAuthenticationConverter converter) {
    this.converter = converter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.oauth2ResourceServer(
            c -> c.jwt(j -> j.jwkSetUri(keySetUri).jwtAuthenticationConverter(converter)))
        .cors(
            c -> {
              CorsConfigurationSource source =
                  request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("localhost"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(List.of("*"));
                    return config;
                  };
              c.configurationSource(source);
            });
    http.authorizeHttpRequests(
        c -> c.requestMatchers("/health").permitAll().anyRequest().authenticated());

    return http.build();
  }
}
