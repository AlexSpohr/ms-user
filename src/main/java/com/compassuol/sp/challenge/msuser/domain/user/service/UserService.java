package com.compassuol.sp.challenge.msuser.domain.user.service;


import com.compassuol.sp.challenge.msuser.domain.user.entity.User;

import com.compassuol.sp.challenge.msuser.domain.user.exception.UserUniqueViolationException;
import com.compassuol.sp.challenge.msuser.domain.user.repository.UserRepository;
import com.compassuol.sp.challenge.msuser.web.consumer.UserConsumerAddress;
import com.compassuol.sp.challenge.msuser.web.dto.NotificationDto;
import com.compassuol.sp.challenge.msuser.web.producer.UserProducerNotification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.compassuol.sp.challenge.msuser.domain.user.enums.Event.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserConsumerAddress userConsumerAddress;
    private final UserProducerNotification userProducerNotification;

    @Transactional
    public User createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            userConsumerAddress.saveAddress(user.getCep());
            userProducerNotification.send(new NotificationDto(user.getEmail(), CREATE));
            return user;
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

    @Transactional
    public void updatePassword(Long id, User user ) {
        User userFound = getUserById(id);
        userFound.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userFound);
        userProducerNotification.send(new NotificationDto(userFound.getEmail(), UPDATE_PASSWORD));
    }

    @Transactional
    public void updateInformation(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setCpf(user.getCpf());
        existingUser.setBirthdate(user.getBirthdate());
        existingUser.setEmail(user.getEmail());
        existingUser.setCep(user.getCep());
        existingUser.setActive(user.getActive());
        userRepository.save(existingUser);
        userProducerNotification.send(new NotificationDto(user.getEmail(), UPDATE));
    }
}