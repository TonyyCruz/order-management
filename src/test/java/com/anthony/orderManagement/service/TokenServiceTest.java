package com.anthony.orderManagement.service;

import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.security.Role;
import com.anthony.orderManagement.exceptions.InvalidTokenException;
import com.anthony.orderManagement.security.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
  private TokenService tokenService;
  private User user;

  @BeforeEach
  void setup() {
    tokenService = new TokenService("test-secret");
    ReflectionTestUtils.setField(tokenService, "issuer", "test-issuer");
    ReflectionTestUtils.setField(tokenService, "expirationTime", Duration.ofHours(2));
    user = new User();
    user.setId(UUID.randomUUID());
    user.setUsername("user_one");
    user.setRole(Role.CUSTOMER);
  }

  @Test
  void generateToken_ShouldCreateValidToken() {
    String token = tokenService.generateToken(user);
    assertNotNull(token);
    DecodedJWT decoded = JWT.decode(token);
    assertEquals("user_one", decoded.getSubject());
    assertEquals("test-issuer", decoded.getIssuer());
    assertEquals(user.getId().toString(), decoded.getClaim("id").asString());
    assertEquals("CUSTOMER", decoded.getClaim("role").asString());
  }

  @Test
  void decodeToken_ShouldReturnDecodedJwt_WhenValid() {
    String token = tokenService.generateToken(user);
    DecodedJWT decoded = tokenService.decodeToken(token);
    assertEquals("user_one", decoded.getSubject());
    assertEquals(user.getId().toString(), decoded.getClaim("id").asString());
    assertEquals("CUSTOMER", decoded.getClaim("role").asString());
  }

  @Test
  void decodeToken_ShouldThrowInvalidTokenException_WhenSignatureInvalid() {
    Algorithm wrongAlgorithm = com.auth0.jwt.algorithms.Algorithm.HMAC256("wrong-secret");
    String fakeToken = JWT.create()
        .withIssuer("test-issuer")
        .withSubject("user_one")
        .sign(wrongAlgorithm);
    assertThrows(InvalidTokenException.class, () -> tokenService.decodeToken(fakeToken));
  }

  @Test
  void generateToken_ShouldSetCorrectExpirationTime() {
    Instant before = Instant.now();
    String token = tokenService.generateToken(user);
    DecodedJWT decoded = JWT.decode(token);
    Instant expiresAt = decoded.getExpiresAt().toInstant();
    Duration diff = Duration.between(before, expiresAt);
    assertTrue(diff.toHours() <= 2 && diff.toHours() >= 1); // tolerância
  }

  @Test
  void decodeToken_ShouldThrowInvalidTokenException_WhenMalformedToken() {
    String malformed = "this.is.not.a.valid.token";
    assertThrows(InvalidTokenException.class, () -> tokenService.decodeToken(malformed));
  }
}
