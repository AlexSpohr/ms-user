//package com.compassuol.sp.challenge.msuser.web.controller;
//
//import com.compassuol.sp.challenge.msuser.domain.user.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static com.compassuol.sp.challenge.msuser.common.UserConstants.USER;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
//class UserControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    UserService userService;
//
//    @Test
//    void createUser_WithValidData_ReturnsCreated() throws Exception {
//        when(userService.createUser(any())).thenReturn(USER);
//
//        mockMvc
//                .perform(
//                        post("/v1/users")
//                                .content(objectMapper.writeValueAsString(USER))
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//    }
//}
