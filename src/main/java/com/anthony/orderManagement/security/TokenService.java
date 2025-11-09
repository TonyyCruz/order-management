package com.anthony.orderManagement.security;

import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.exceptions.InvalidTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Duration;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
  @Value("${jwt.security.token.expiration-time}")
  private Duration expirationTime;
  @Value("${jwt.security.token.issuer}")
  private String issuer;
  private final Algorithm algorithm;

  public TokenService(@Value("${jwt.security.token.secret}") String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  /**
   * Generate token string.
   *
   * @param user the user
   * @return the string
   */
  public String generateToken(User user) {
    return JWT.create()
        .withIssuer(issuer)
        .withSubject(user.getUsername())
        .withClaim("id", user.getId().toString())
        .withClaim("role", user.getRole().name())
        .withExpiresAt(generateExpiration())
        .sign(algorithm);
  }

  public DecodedJWT decodeToken(String token) {
    try {
      return JWT.require(algorithm)
          .withIssuer(issuer)
          .build()
          .verify(token);
    } catch (JWTVerificationException e) {
      throw new InvalidTokenException();
    }
  }

  private Instant generateExpiration() {
    return Instant.now().plus(expirationTime);
  }
}
