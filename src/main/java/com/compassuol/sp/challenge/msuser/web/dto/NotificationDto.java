package com.compassuol.sp.challenge.msuser.web.dto;

import com.compassuol.sp.challenge.msuser.domain.user.enums.Event;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class NotificationDto implements Serializable{

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Event event;

    @NotBlank
    private Date date;

    public NotificationDto(String email, Event event) {
        this.email = email;
        this.event = event;
        this.date = Date.from(Instant.now());
    }

}
