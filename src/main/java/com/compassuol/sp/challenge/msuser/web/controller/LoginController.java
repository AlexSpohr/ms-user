package com.compassuol.sp.challenge.msuser.web.controller;

import com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtToken;
import com.compassuol.sp.challenge.msuser.domain.user.service.AuthenticationService;
import com.compassuol.sp.challenge.msuser.web.dto.LoginCreateDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserResponseDto;
import com.compassuol.sp.challenge.msuser.web.dto.mapper.LoginMapper;
import com.compassuol.sp.challenge.msuser.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/login")
public class LoginController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Login", description = "Feature to login a user by email and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully logged in",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<JwtToken> loginUser(@RequestBody @Valid LoginCreateDto loginCreateDto) {
        JwtToken token = authenticationService.authenticate(LoginMapper.toLogin(loginCreateDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }
}
