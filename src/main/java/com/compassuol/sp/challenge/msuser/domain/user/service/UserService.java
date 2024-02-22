package com.compassuol.sp.challenge.msuser.domain.user.service;

import com.compassuol.sp.challenge.msuser.domain.user.entity.User;

import com.compassuol.sp.challenge.msuser.domain.user.exception.UserUniqueViolationException;
import com.compassuol.sp.challenge.msuser.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public User createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserUniqueViolationException("CPF/Email already exists in the system.");
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found.")
        );
    }
}