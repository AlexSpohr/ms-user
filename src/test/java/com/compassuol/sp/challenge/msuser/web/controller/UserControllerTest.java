package com.compassuol.sp.challenge.msuser.web.controller;

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
        UserCreateDto validUser = new UserCreateDto();
        validUser.setFirstName("Nome");
        validUser.setLastName("Sobrenome");
        validUser.setCpf("589.410.480-79");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2000-04-04");
        validUser.setBirthdate(date);
        validUser.setEmail("teste16@email.com");
        validUser.setCep("98910-000");
        validUser.setPassword("12345678");
        validUser.setActive(true);

        when(userService.createUser(any())).thenReturn(USER_RESPONSE);

        mockMvc
                .perform(
                        post("/v1/users")
                                .content(objectMapper.writeValueAsString(validUser))
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
        Date date;
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

    @Test
    void getUserById_WithExistingId_ReturnsOk200() throws Exception {
        long id = 1L;
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        when(userService.getUserAddressById(any())).thenReturn(USER_RESPONSE);

        mockMvc
                .perform(
                        get("/v1/users/" + id)
                                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZTJAZW1haWwuY29tIiwiaWF0IjoxNzA5MjMxOTY0LCJleHAiOjE3MDkyMzI1NjR9.VxjeDLGyZsFQle5yIml-bmYg-23JmOtvL33QbDCiE98")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getUserById_WithNonExistingId_ReturnsNotFound404() throws Exception {
        long nonExistingId = -1L;

        when(userService.getUserAddressById(any())).thenThrow(new EntityNotFoundException("User not found."));

        mockMvc
                .perform(
                        get("/v1/users/" + nonExistingId)
                                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZTJAZW1haWwuY29tIiwiaWF0IjoxNzA5MjMxOTY0LCJleHAiOjE3MDkyMzI1NjR9.VxjeDLGyZsFQle5yIml-bmYg-23JmOtvL33QbDCiE98")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserById_WithNotAuthorizedId_ReturnsForbidden403() throws Exception {
        long unauthorizedId = 1L;

        when(userService.getUserAddressById(any())).thenThrow(new AccessDeniedException("User is not authorized to access this resource."));

        mockMvc
                .perform(
                        get("/v1/users/" + unauthorizedId)
                                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZTJAZW1haWwuY29tIiwiaWF0IjoxNzA5MjMxOTY0LCJleHAiOjE3MDkyMzI1NjR9.VxjeDLGyZsFQle5yIml-bmYg-23JmOtvL33QbDCiE98")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void updatePassword_WithExistingId_ReturnsOk200() throws Exception {
        long validId = 1L;
        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword("newPassword");

        doNothing().when(userService).updatePassword(eq(validId), any());

        mockMvc
                .perform(
                        put("/v1/users/" + validId + "/password")
                                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZTJAZW1haWwuY29tIiwiaWF0IjoxNzA5MjMxOTY0LCJleHAiOjE3MDkyMzI1NjR9.VxjeDLGyZsFQle5yIml-bmYg-23JmOtvL33QbDCiE98")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userPasswordDto)))
                .andExpect(status().isOk());
    }

    @Test
    void updatePassword_WithNonExistingId_ReturnsNotFound404() throws Exception {
        long nonExistingId = -1L;
        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword("newPassword");

        doThrow(new EntityNotFoundException("User not found.")).when(userService).updatePassword(eq(nonExistingId), any());

        mockMvc
                .perform(
                        put("/v1/users/" + nonExistingId + "/password")
                                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZTJAZW1haWwuY29tIiwiaWF0IjoxNzA5MjMxOTY0LCJleHAiOjE3MDkyMzI1NjR9.VxjeDLGyZsFQle5yIml-bmYg-23JmOtvL33QbDCiE98")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userPasswordDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePassword_WithInvalidParams_ReturnsUnprocessableEntity422() throws Exception {
        long validId = 1L;
        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword(null);

        doThrow(new IllegalArgumentException("Password cannot be null.")).when(userService).updatePassword(eq(validId), any());

        mockMvc
                .perform(
                        put("/v1/users/" + validId + "/password")
                                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZTJAZW1haWwuY29tIiwiaWF0IjoxNzA5MjMxOTY0LCJleHAiOjE3MDkyMzI1NjR9.VxjeDLGyZsFQle5yIml-bmYg-23JmOtvL33QbDCiE98")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userPasswordDto)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updatePassword_WithNotAuthorizedId_ReturnsForbidden403() throws Exception {
        long notAuthorizedId = 1L;
        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword("newPassword");

        doThrow(new AccessDeniedException("User is not authorized to access this resource.")).when(userService).updatePassword(eq(notAuthorizedId), any());

        mockMvc
                .perform(
                        put("/v1/users/" + notAuthorizedId + "/password")
                                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZTJAZW1haWwuY29tIiwiaWF0IjoxNzA5MjMxOTY0LCJleHAiOjE3MDkyMzI1NjR9.VxjeDLGyZsFQle5yIml-bmYg-23JmOtvL33QbDCiE98")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userPasswordDto)))
                .andExpect(status().isForbidden());
    }
}
