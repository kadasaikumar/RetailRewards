package com.chc.retail.config;

import com.chc.retail.service.SystemUser;
import com.chc.retail.util.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired private JwtProvider tokenProvider;
  @Autowired private SystemUser userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = getJwtFromRequest(request);
      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

        String username = tokenProvider.getUserUsernameFromJWT(jwt);
        log.info("user Name found in token {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.info("user Details found in token {}", userDetails.getUsername());
        log.info("user Role found in token {}", userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception ex) {
      log.error("Could not set user authentication in security context {}", ex);
    }
    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      log.info("Auth Token Present !! {}", bearerToken.substring(7));
      return bearerToken.substring(7);
    }
    return null;
  }
}
