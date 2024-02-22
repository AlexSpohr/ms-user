package com.compassuol.sp.challenge.msuser.domain.user.service;

import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtToken;
import com.compassuol.sp.challenge.msuser.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import static com.compassuol.sp.challenge.msuser.domain.user.jwt.JwtUtils.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


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

        return tokenGenerate(user.getEmail());
    }


}
