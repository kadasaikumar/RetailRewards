package com.chc.retail.security;

import com.chc.retail.entity.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
  public static UserPrincipal userToPrincipal(AppUser user) {
    UserPrincipal userp = new UserPrincipal();
    String role1 = user.getRole();

    List<String> strings = Arrays.asList(role1.split(","));
    List<SimpleGrantedAuthority> authorities =
        strings.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());

    userp.setUsername(user.getUsername());
    userp.setPassword(user.getPassword());
    userp.setEnabled(user.isEnabled());
    userp.setAuthorities(authorities);
    return userp;
  }
}
