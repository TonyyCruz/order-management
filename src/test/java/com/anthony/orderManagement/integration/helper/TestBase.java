package com.anthony.orderManagement.integration.helper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.anthony.orderManagement.controler.dto.login.LoginRequest;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
public class TestBase {
  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  protected final String AUTH_LOGIN_URL = "/auth/login";
  protected final String USER_URL = "/users";
  protected LoginRequest userLogin = new LoginRequest("user", "123456");
  protected LoginRequest adminLogin = new LoginRequest("admin", "loginAdmin");

  public String performLogin(String username, String password) throws Exception {
    ResultActions resultActions = mockMvc.perform(post(AUTH_LOGIN_URL).with(httpBasic(username, password)));
    MvcResult mvcResult = resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();
    JSONObject json = new JSONObject(contentAsString);
    return "Bearer " + json.getString("token");
  }

  public User performSaveUser(User entity) {
    entity.setId(null);
    entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    return userRepository.save(entity);
  }
}
