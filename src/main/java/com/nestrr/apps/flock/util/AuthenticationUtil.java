package com.nestrr.apps.flock.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public class AuthenticationUtil {
  public static String getJwtClaim(Authentication a, String claim) {
    return ((Jwt) a.getPrincipal()).getClaimAsString(claim);
  }

  public static String getJwtId(Authentication a) {
    return getJwtClaim(a, "sub");
  }

  public static List<String> getRoles(Authentication a) {
    return a.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(roleName -> roleName.split("ROLE_")[1])
        .toList();
  }
}
