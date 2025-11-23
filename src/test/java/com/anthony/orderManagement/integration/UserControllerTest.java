package com.anthony.orderManagement.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anthony.orderManagement.controler.dto.login.LoginRequest;
import com.anthony.orderManagement.controler.dto.user.PasswordUpdateDto;
import com.anthony.orderManagement.controler.dto.user.UserUpdateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.helper.mocks.MockUser;
import com.anthony.orderManagement.integration.helper.TestBase;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class UserControllerTest extends TestBase {
  private static final String USER_URL = "/users/me";
  private User user;
  private String userToken;

  @BeforeEach
  void setUp() {
    userToken = performLogin(userLogin.username(), userLogin.password());
    user = userRepository.findByUsername(userLogin.username())
        .orElseThrow(() -> new IllegalStateException("User not found in test DB"));
  }

  @Nested
  @DisplayName("Happy Path")
  class UserControllerHappyPath {

    @Test
    @DisplayName("Get Current User returns user details successfully")
    void getCurrentUser_returnsUserDetailsSuccessfully() throws Exception {
      mockMvc.perform(get(USER_URL)
              .header("Authorization", userToken))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.role").value(user.getRole().name()))
          .andExpect(jsonPath("$.birthDate").value(user.getBirthDate().toString()))
          .andExpect(jsonPath("$.username").value(user.getUsername()))
          .andExpect(jsonPath("$.password").doesNotExist())
          .andDo(print());
    }

    @Test@DisplayName("Update Current User updates user data successfully")
    void updateCurrentUser_updatesUserDataSuccessfully() throws Exception {
      UserUpdateDto dto = MockUser.userUpdateDto();
      String valueAsString = objectMapper.writeValueAsString(dto);
      mockMvc.perform(put(USER_URL)
              .header("Authorization", userToken)
              .contentType(MediaType.APPLICATION_JSON)
              .content(valueAsString))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(user.getId().toString()))
          .andExpect(jsonPath("$.username").value(dto.username()))
          .andExpect(jsonPath("$.birthDate").value(dto.birthDate().toString()))
          .andExpect(jsonPath("$.password").doesNotExist())
          .andDo(print());
    }

    @Test
    @DisplayName("Update Current User can update their data using the same username")
    void updateCurrentUser_updatesTheirDataUsingSameUsername() throws Exception {
      UserUpdateDto dto = new UserUpdateDto(user.getUsername(),
          LocalDate.parse("1800-01-01"));
      String valueAsString = objectMapper.writeValueAsString(dto);
      mockMvc.perform(put(USER_URL)
              .header("Authorization", userToken)
              .contentType(MediaType.APPLICATION_JSON)
              .content(valueAsString))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(user.getId().toString()))
          .andExpect(jsonPath("$.username").value(dto.username()))
          .andExpect(jsonPath("$.birthDate").value(dto.birthDate().toString()))
          .andExpect(jsonPath("$.password").doesNotExist())
          .andDo(print());
    }

    @Test
    @DisplayName("Update password can be done successfully with correct current password")
    void updatePassword_canBeDoneSuccessfully_withCorrectCurrentPassword() throws Exception {
      String newPassword = "NewP@ssw0rd!";
      PasswordUpdateDto dto = new PasswordUpdateDto(userLogin.password(), newPassword);
      String valueAsString = objectMapper.writeValueAsString(dto);
      mockMvc.perform(put(USER_URL + "/password")
              .header("Authorization", userToken)
              .contentType(MediaType.APPLICATION_JSON)
              .content(valueAsString))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(user.getId().toString()))
          .andExpect(jsonPath("$.username").value(user.getUsername()))
          .andExpect(jsonPath("$.birthDate").value(user.getBirthDate().toString()))
          .andExpect(jsonPath("$.password").doesNotExist())
          .andDo(print());
      LoginRequest loginRequest = new LoginRequest(userLogin.username(), newPassword);
      String loginAsString = objectMapper.writeValueAsString(loginRequest);
      mockMvc.perform(post(AUTH_LOGIN_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(loginAsString))
          .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Current User deletes user successfully")
    void deleteCurrentUser_deletesUserSuccessfully() throws Exception {
      mockMvc.perform(delete(USER_URL)
              .header("Authorization", userToken))
          .andExpect(status().isNoContent())
          .andDo(print());
      mockMvc.perform(get(USER_URL)
              .header("Authorization", userToken))
          .andExpect(status().isUnauthorized())
          .andDo(print());
    }
  }

  @Nested
  @DisplayName("Exception Path")
  class UserControllerExceptionPath {

  }
}
