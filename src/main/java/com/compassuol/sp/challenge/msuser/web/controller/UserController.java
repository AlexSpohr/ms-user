package com.compassuol.sp.challenge.msuser.web.controller;


import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.compassuol.sp.challenge.msuser.domain.user.service.UserService;
import com.compassuol.sp.challenge.msuser.web.dto.UserCreateDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserPasswordDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserResponseDto;
import com.compassuol.sp.challenge.msuser.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        User user = userService.createUser(UserMapper.toUser(userCreateDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto userPasswordDto) {
        userService.updatePassword(id, UserMapper.toUpdatePassword(userPasswordDto));
        return ResponseEntity.ok().build();
    }
}
