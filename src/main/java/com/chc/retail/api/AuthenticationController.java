package com.chc.retail.api;
;
import com.chc.retail.dto.JwtResponse;
import com.chc.retail.dto.LoginDto;
import com.chc.retail.util.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final JwtProvider tokenProvider;

  public AuthenticationController(
      AuthenticationManager authenticationManager, JwtProvider tokenProvider) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginDto login) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
  }
}
