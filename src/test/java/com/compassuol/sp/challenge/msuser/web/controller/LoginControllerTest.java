package com.compassuol.sp.challenge.msuser.web.controller;

import com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtToken;
import com.compassuol.sp.challenge.msuser.domain.user.service.AuthenticationService;
import com.compassuol.sp.challenge.msuser.web.dto.LoginCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.compassuol.sp.challenge.msuser.common.UserConstants.USER_EMAIL;
import static com.compassuol.sp.challenge.msuser.common.UserConstants.USER_PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = LoginController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthenticationService authenticationService;


    @Test
    void loginUser_WithValidUser_ReturnsOk200() throws Exception {
        LoginCreateDto validLogin = new LoginCreateDto();
        validLogin.setEmail(USER_EMAIL);
        validLogin.setPassword(USER_PASSWORD);

        when(authenticationService.authenticate(any())).thenReturn(new JwtToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZTJAZW1haWwuY29tIiwiaWF0IjoxNzA5MjMxOTY0LCJleHAiOjE3MDkyMzI1NjR9.VxjeDLGyZsFQle5yIml-bmYg-23JmOtvL33QbDCiE98"));

        mockMvc
                .perform(
                        post("/v1/login")
                                .content(objectMapper.writeValueAsString(validLogin))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void loginUser_WithInvalidParams_ReturnsUnprocessableEntity422() throws Exception {
        LoginCreateDto invalidLogin = new LoginCreateDto();
        invalidLogin.setEmail("invalidEmail");
        invalidLogin.setPassword("invalidPassword");

        when(authenticationService.authenticate(any())).thenThrow(new IllegalArgumentException("Invalid email or password"));

        mockMvc
                .perform(
                        post("/v1/login")
                                .content(objectMapper.writeValueAsString(invalidLogin))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}
