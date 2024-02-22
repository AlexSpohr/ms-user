package com.compassuol.sp.challenge.msuser.web.controller;

import com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtToken;
import com.compassuol.sp.challenge.msuser.domain.user.service.AuthenticationService;
import com.compassuol.sp.challenge.msuser.web.dto.LoginCreateDto;
import com.compassuol.sp.challenge.msuser.web.dto.mapper.LoginMapper;
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

    @PostMapping
    public ResponseEntity<JwtToken> loginUser(@RequestBody @Valid LoginCreateDto loginCreateDto) {
        JwtToken token = authenticationService.authenticate(LoginMapper.toLogin(loginCreateDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }
}
