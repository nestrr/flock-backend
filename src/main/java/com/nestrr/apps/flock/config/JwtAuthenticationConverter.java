package com.nestrr.apps.flock.config;

import com.nestrr.apps.flock.profile.repository.RoleAssignmentRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

  private final RoleAssignmentRepository roleAssignmentRepository;

  public JwtAuthenticationConverter(RoleAssignmentRepository roleAssignmentRepository) {
    this.roleAssignmentRepository = roleAssignmentRepository;
  }

  @Override
  public JwtAuthenticationToken convert(@NonNull Jwt source) {
    Collection<GrantedAuthority> authorities =
        new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_student")));
    String subId = "";
    if (source.hasClaim("sub")) {
      subId = source.getClaimAsString("sub");
    } else {
      return new JwtAuthenticationToken(source, authorities);
    }

    Optional<List<String>> roles = roleAssignmentRepository.listRolesByPersonId(subId);
    if (roles.isPresent() && !roles.get().isEmpty()) {
      authorities =
          roles.get().stream()
              .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
              .collect(Collectors.toList());
    }
    System.out.println("AUTHORITIES ARE SET TO " + authorities);
    return new JwtAuthenticationToken(source, authorities);
  }
}
