package com.compassuol.sp.challenge.msuser.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class LoginCreateDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;
}
