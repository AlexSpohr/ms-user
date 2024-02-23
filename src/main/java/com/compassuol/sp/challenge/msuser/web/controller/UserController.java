package com.compassuol.sp.challenge.msuser.web.controller;


import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.compassuol.sp.challenge.msuser.domain.user.service.UserService;
import com.compassuol.sp.challenge.msuser.web.dto.UserUpdateDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserCreateDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserPasswordDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserResponseDto;
import com.compassuol.sp.challenge.msuser.web.dto.mapper.UserMapper;
import com.compassuol.sp.challenge.msuser.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Feature to create a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "User already registered in the system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        User user = userService.createUser(UserMapper.toEntity(userCreateDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.toDto(user));
    }

    @Operation(summary = "Find by id", description = "Feature to find a user by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Update password", description = "Feature to update password of a user by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto userPasswordDto) {
        userService.updatePassword(id, UserMapper.toEntity(userPasswordDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        userService.updateInformation(id, UserMapper.toEntity(userUpdateDto));
        return ResponseEntity.ok().build();
    }
}
