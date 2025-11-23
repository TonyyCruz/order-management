package com.anthony.orderManagement.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anthony.orderManagement.controler.dto.login.LoginRequest;
import com.anthony.orderManagement.controler.dto.user.UserCreateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.helper.mocks.MockUser;
import com.anthony.orderManagement.integration.helper.TestBase;
import com.anthony.orderManagement.security.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jayway.jsonpath.JsonPath;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

@Tag("integration")
@DisplayName("Integration test for auth controller")
class AuthControllerTest extends TestBase {
  private final static String AUTH_REGISTER_URL = "/auth/register";
  private User user;

  @BeforeEach
  protected void setUp() {
    user = userRepository.findByUsername(userLogin.username())
        .orElseThrow(() -> new IllegalStateException("User not found in test DB"));
  }

  @Nested
  @DisplayName("Happy Path")
  class AuthControllerHappyPath {

    @Test
    @DisplayName("Register creates a new user successfully")
    void register_createsNewUserSuccessfully() throws Exception {
      UserCreateDto dto = MockUser.userCreateDto();
      String valueAsString = objectMapper.writeValueAsString(dto);
      MvcResult result = mockMvc.perform(post(AUTH_REGISTER_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(valueAsString))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.username").value(dto.username()))
          .andExpect(jsonPath("$.role").value(Role.CUSTOMER.name()))
          .andExpect(jsonPath("$.birthDate").value(dto.birthDate().toString()))
          .andExpect(jsonPath("$.password").doesNotExist())
          .andDo(print())
          .andReturn();
    }

    @Test
    @DisplayName("Login returns valid token when credentials are correct")
    void login_returnsValidToken_whenCredentialsAreCorrect() throws Exception {
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
  }

  @Nested
  @DisplayName("Exception Path")
  class AuthControllerExceptionPath {

    @Test
    @DisplayName("Login returns 401 when username is invalid")
    void login_shouldReturn401_whenUsernameIsInvalid() throws Exception {
      String valueAsString = objectMapper.writeValueAsString(
          new LoginRequest("invalid", userLogin.password()));
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

    @Test
    @DisplayName("Register returns 400 when username already exists")
    void register_shouldReturn400_whenUsernameAlreadyExists() throws Exception {
      UserCreateDto dto = MockUser.userCreateDto();
      dto = new UserCreateDto(
          user.getUsername(),
          dto.password(),
          dto.birthDate()
      );
      String valueAsString = objectMapper.writeValueAsString(dto);
      mockMvc.perform(post(AUTH_REGISTER_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(valueAsString))
          .andExpect(status().isBadRequest())
          .andDo(print());
    }

    @Test
    @DisplayName("Register returns 400 when username is invalid")
    void register_shouldReturn400_whenUsernameIsInvalid() throws Exception {
      String[] wrongUsernames = {"", "   "};
      for (String username : wrongUsernames) {
        UserCreateDto dto = new UserCreateDto(
            "  ",
            MockUser.userCreateDto().password(),
            MockUser.userCreateDto().birthDate()
        );
        String valueAsString = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post(AUTH_REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString))
            .andExpect(status().isBadRequest())
            .andDo(print());
      }
    }

    @Test
    @DisplayName("Register returns 400 when birthdate is invalid")
    void register_shouldReturn400_whenBirthdateIsInvalid() throws Exception {
      LocalDate[] wrongDates = {
          LocalDate.now().plusDays(1),
          LocalDate.now()
      };
      for (LocalDate date : wrongDates) {
        UserCreateDto dto = new UserCreateDto(
            MockUser.userCreateDto().username(),
            MockUser.userCreateDto().password(),
            date
        );
        String valueAsString = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post(AUTH_REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString))
            .andExpect(status().isBadRequest())
            .andDo(print());
      }
    }

    @Test
    @DisplayName("Register returns 400 when password is invalid")
    void register_shouldReturn400_whenPasswordIsInvalid() throws Exception {
      String[] wrongPass = {"short1#", "alllowercase1!", "ALLUPPERCASE1!", "NoNumbers!",
          "NoSpecialChar1"};
      for (String pwd : wrongPass) {
        UserCreateDto dto = new UserCreateDto(
            MockUser.userCreateDto().username(),
            pwd,
            MockUser.userCreateDto().birthDate()
        );
        String valueAsString = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post(AUTH_REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString))
            .andExpect(status().isBadRequest())
            .andDo(print());
      }
    }
  }
}
