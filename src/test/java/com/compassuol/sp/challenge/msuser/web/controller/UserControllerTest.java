package com.compassuol.sp.challenge.msuser.web.controller;

import com.compassuol.sp.challenge.msuser.TestUtil;
import com.compassuol.sp.challenge.msuser.domain.user.exception.UserUniqueViolationException;
import com.compassuol.sp.challenge.msuser.domain.user.service.UserService;
import com.compassuol.sp.challenge.msuser.web.dto.UserCreateDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserPasswordDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.AccessDeniedException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.compassuol.sp.challenge.msuser.common.UserConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;


    @Test
    void createUser_WithValidParams_ReturnsCreated201() throws Exception {
        String request = TestUtil.readStringFromFile("json/UserResponseDto.json");

        when(userService.createUser(any())).thenReturn(USER_RESPONSE);

        mockMvc
                .perform(
                        post("/v1/users")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUser_WithExistingUser_ReturnsConflict409() throws Exception {
        UserCreateDto existingUser = getUserCreateDto();

        when(userService.createUser(any())).thenThrow(UserUniqueViolationException.class);

        mockMvc.perform(
                        post("/v1/users")
                                .content(objectMapper.writeValueAsString(existingUser))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    private static UserCreateDto getUserCreateDto() {
        UserCreateDto existingUser = new UserCreateDto();
        existingUser.setFirstName("Nome");
        existingUser.setLastName("Sobrenome");
        existingUser.setCpf("589.410.480-79");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2000-04-04");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        existingUser.setBirthdate(date);
        existingUser.setEmail("teste16@email.com");
        existingUser.setCep("98910-000");
        existingUser.setPassword("12345678");
        existingUser.setActive(true);
        return existingUser;
    }

    @Test
    public void createUser_WithInvalidParams_ReturnsUnprocessableEntity422() throws Exception {
        UserCreateDto invalidUserCreateDto = new UserCreateDto();
        invalidUserCreateDto.setFirstName("");

        when(userService.createUser(any())).thenThrow(new RuntimeException("Invalid data"));

        String invalidRequest = objectMapper.writeValueAsString(invalidUserCreateDto);

        mockMvc
                .perform(
                        post("/v1/users")
                                .content(invalidRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }



}
