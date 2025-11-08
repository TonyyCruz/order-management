package com.anthony.orderManagement.controler.dto.login;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record LoginRequest(String username, String password) {

  public UsernamePasswordAuthenticationToken toAuthentication() {
    return new UsernamePasswordAuthenticationToken(username, password);
  }
}
