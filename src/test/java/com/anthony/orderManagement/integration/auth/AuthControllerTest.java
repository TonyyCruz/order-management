package com.anthony.orderManagement.integration.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anthony.orderManagement.controler.dto.login.LoginRequest;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.integration.helper.TestBase;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

@Tag("integration")
@DisplayName("Integration test for login endpoint")
class LoginIntegrationTest extends TestBase {
  private User user;

  @BeforeEach
  protected void setUp() {
    user = userRepository.findByUsername(userLogin.username())
        .orElseThrow(() -> new IllegalStateException("User not found in test DB"));
  }

  @Test
  @DisplayName("Login returns valid token when credentials are correct")
  void login_ReturnsValidToken_whenCredentialsAreCorrect() throws Exception {
    String valueAsString = objectMapper.writeValueAsString(userLogin);

    MvcResult result = mockMvc.perform(post(AUTH_LOGIN_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
        .andExpect(status().isOk())
        .andReturn();

    String json = result.getResponse().getContentAsString();
    String token = JsonPath.read(json, "$.token");

    assertNotNull(token, "Token should not be null");

    DecodedJWT decoded = JWT.decode(token);

    assertEquals(user.getUsername(), decoded.getSubject());
    assertEquals(user.getId().toString(), decoded.getClaim("id").asString());
    assertEquals(user.getRole().name(), decoded.getClaim("role").asString());
  }

  @Test
  @DisplayName("Login returns 401 when username is invalid")
  void login_shouldReturn401_whenUsernameIsInvalid() throws Exception {
    String valueAsString = objectMapper.writeValueAsString(
        new LoginRequest("errorUser", "99999999"));

    mockMvc.perform(post(AUTH_LOGIN_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
        .andExpect(status().isUnauthorized())
        .andDo(print());
  }

  @Test
  @DisplayName("Login returns 401 when password is invalid")
  void login_shouldReturn401_whenPasswordIsInvalid() throws Exception {
    String valueAsString = objectMapper.writeValueAsString(
        new LoginRequest(userLogin.username(), "wrongPassword"));
    mockMvc.perform(post(AUTH_LOGIN_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
        .andExpect(status().isUnauthorized())
        .andDo(print());
  }

}
