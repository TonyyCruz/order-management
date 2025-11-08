package com.anthony.orderManagement.controler;

import com.anthony.orderManagement.controler.dto.login.LoginRequest;
import com.anthony.orderManagement.controler.dto.login.TokenDto;
import com.anthony.orderManagement.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authManager;
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authManager.authenticate(loginRequest.toAuthentication());
    String token = tokenService.generateToken(authentication.getName());
    return ResponseEntity.ok(new TokenDto(token));
  }

}
