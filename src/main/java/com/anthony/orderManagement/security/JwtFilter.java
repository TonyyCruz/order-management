package com.anthony.orderManagement.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    Optional<String> token = extractToken(request);
    if (token.isPresent()) {
      DecodedJWT decodedJWT = tokenService.decodeToken(token.get());
      String username = decodedJWT.getSubject();
      UUID userId = UUID.fromString(decodedJWT.getClaim("id").asString());
      User user = (User) userDetailsService.loadUserByUsername(username);
      var usernameAuthentication = new UsernamePasswordAuthenticationToken(
          user, null, user.getAuthorities());
      usernameAuthentication.setDetails(userId);
      SecurityContextHolder.getContext().setAuthentication(usernameAuthentication);
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Extract token optional.
   *
   * @param request the request
   * @return the optional
   */
  public Optional<String> extractToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader("Authorization"))
        .filter(header -> header.startsWith("Bearer "))
        .map(header -> header.substring(7));
  }
}
