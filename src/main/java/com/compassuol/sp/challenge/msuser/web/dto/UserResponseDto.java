package com.compassuol.sp.challenge.msuser.web.dto;


import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserResponseDto {
    private Long id;
    private AddressResponseDto address;
    private String firstName;
    private String lastName;
    private String cpf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthdate;
    private String email;
    private Boolean active;

    public void setUserFromDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.cpf = user.getCpf();
        this.birthdate = user.getBirthdate();
        this.email = user.getEmail();
        this.active = user.getActive();
    }
}
