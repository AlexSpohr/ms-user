package com.compassuol.sp.challenge.msuser.domain.user.service;

import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtToken;
import com.compassuol.sp.challenge.msuser.domain.user.repository.UserRepository;
import com.compassuol.sp.challenge.msuser.web.dto.NotificationDto;
import com.compassuol.sp.challenge.msuser.web.producer.UserProducerNotification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import static com.compassuol.sp.challenge.msuser.domain.user.enums.Event.LOGIN;
import static com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtUtils.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserProducerNotification userProducerNotification;


    public JwtToken authenticate(User request){
        log.info("Authenticating user: {}, {}", request.getEmail(), request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User not Found")
        );

        userProducerNotification.send(new NotificationDto(user.getEmail(), LOGIN));
        return tokenGenerate(user.getEmail());
    }
}
