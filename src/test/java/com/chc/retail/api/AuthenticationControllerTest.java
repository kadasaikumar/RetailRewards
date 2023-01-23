package com.chc.retail.api;

import com.chc.retail.dto.LoginDto;
import com.chc.retail.util.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthenticationController.class})
@ActiveProfiles({"DEV"})
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {
  @Autowired private AuthenticationController authenticationController;

  @MockBean private AuthenticationManager authenticationManager;

  @MockBean private JwtProvider jwtProvider;

  @Test
  void testAuthenticateUser() throws Exception {
    LoginDto loginDto = new LoginDto();
    loginDto.setUserName("janedoe");
    loginDto.setPassword("iloveyou");
    String content = (new ObjectMapper()).writeValueAsString(loginDto);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(this.authenticationController)
            .build()
            .perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
  }
}
