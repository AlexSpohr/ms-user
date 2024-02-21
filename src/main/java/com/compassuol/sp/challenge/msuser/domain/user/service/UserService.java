package com.compassuol.sp.challenge.msuser.domain.user.service;

import com.compassuol.sp.challenge.msuser.domain.user.entity.User;

import com.compassuol.sp.challenge.msuser.domain.user.exception.UserUniqueViolationException;
import com.compassuol.sp.challenge.msuser.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserUniqueViolationException("CPF/Email already exists in the system.");
        }
    }
}
