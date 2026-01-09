package com.anthony.blacksmithOnlineStore.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anthony.blacksmithOnlineStore.controler.dto.blacksmith.BlacksmithRequestDto;
import com.anthony.blacksmithOnlineStore.entity.Blacksmith;
import com.anthony.blacksmithOnlineStore.helper.mocks.MockBlacksmith;
import com.anthony.blacksmithOnlineStore.integration.helper.TestBase;
import com.anthony.blacksmithOnlineStore.repository.BlacksmithRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@Tag("integration")
@DisplayName("Integration test for Blacksmith controller")
public class BlacksmithControllerTest extends TestBase {
  @Autowired
  private BlacksmithRepository blacksmithRepository;
  private final String BLACKSMITH_BASE_URL = "/blacksmiths";
  private Blacksmith blacksmith;

  @BeforeEach
  void setUp() {
    blacksmith = blacksmithRepository.findById(1L)
        .orElseThrow(() -> new IllegalStateException("Blacksmith not found in test DB"));
  }

  @Nested
  @Transactional
  @DisplayName("Happy Path")
  class BlacksmithControllerHappyPath {

    private String adminToken;

    @BeforeEach
    void setUp() throws Exception {
      adminToken = performLogin(adminLogin);
    }

    @Test
    @Transactional
    @DisplayName("Can create blacksmith successfully")
    void createBlacksmith_canCreateBlacksmithSuccessfully() throws Exception {
      String valueAsString = objectMapper.writeValueAsString(MockBlacksmith.requestDto());
      mockMvc.perform(post(BLACKSMITH_BASE_URL)
              .header("Authorization", adminToken)
              .contentType(MediaType.APPLICATION_JSON)
              .content(valueAsString))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.name").value(MockBlacksmith.requestDto().name()))
          .andExpect(jsonPath("$.description").value(MockBlacksmith.requestDto().description()))
          .andExpect(jsonPath("$.ratingCount").value(0))
          .andExpect(jsonPath("$.ratingAverage").value(Matchers.nullValue()));
    }

    @Test
    @Transactional
    @DisplayName("Can update blacksmith successfully")
    void updateBlacksmith_canUpdateBlacksmithSuccessfully() throws Exception {
      var updateDto = new BlacksmithRequestDto("Updated Name", "Updated Description");
      String valueAsString = objectMapper.writeValueAsString(updateDto);
      String updateUrl = BLACKSMITH_BASE_URL + "/" + blacksmith.getId();
      mockMvc.perform(put(updateUrl)
              .header("Authorization", adminToken)
              .contentType(MediaType.APPLICATION_JSON)
              .content(valueAsString))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.name").value(updateDto.name()))
          .andExpect(jsonPath("$.description").value(updateDto.description()))
          .andExpect(jsonPath("$.ratingCount").value(blacksmith.getRatingCount()))
          .andExpect(jsonPath("$.ratingAverage").value(blacksmith.getRatingAverage()));
    }
  }

  @Nested
  @DisplayName("Exception Path")
  class BlacksmithControllerExceptionPath {

    private String userToken;

    @BeforeEach
    void setUp() throws Exception {
      userToken = performLogin(userLogin);
    }
  }
}
