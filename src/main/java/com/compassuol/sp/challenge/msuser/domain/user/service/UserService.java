package com.compassuol.sp.challenge.msuser.domain.user.service;


import com.compassuol.sp.challenge.msuser.domain.user.entity.User;

import com.compassuol.sp.challenge.msuser.domain.user.exception.UserUniqueViolationException;
import com.compassuol.sp.challenge.msuser.domain.user.repository.UserRepository;
import com.compassuol.sp.challenge.msuser.web.dto.*;
import com.compassuol.sp.challenge.msuser.web.producer.UserProducerAddress;
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
    private final UserProducerAddress userProducerAddress;
    private final UserProducerNotification userProducerNotification;

    @Transactional
    public UserResponseDto createUser(UserCreateDto userDto) {
        try {
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setCpf(userDto.getCpf());
            user.setBirthdate(userDto.getBirthdate());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setActive(userDto.getActive());

            AddressDto address = userProducerAddress.saveAddress(userDto.getCep());
            user.setAddresses_id(address.getId());

            AddressResponseDto addressResponseDto = new AddressResponseDto();
            addressResponseDto.setAddressFromDto(address);

            userRepository.save(user);
            userProducerNotification.send(new NotificationDto(user.getEmail(), CREATE));

            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setAddress(addressResponseDto);
            userResponseDto.setUserFromDto(user);

            return userResponseDto;
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
    public void updateInformation(Long id, UserUpdateDto userDto) {
        User existingUser = getUserById(id);
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setCpf(userDto.getCpf());
        existingUser.setBirthdate(userDto.getBirthdate());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setActive(userDto.getActive());
        userRepository.save(existingUser);

        AddressDto address = userProducerAddress.saveAddress(userDto.getCep());
        existingUser.setAddresses_id(address.getId());

        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setAddressFromDto(address);

        userProducerNotification.send(new NotificationDto(userDto.getEmail(), UPDATE));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setAddress(addressResponseDto);
        userResponseDto.setUserFromDto(existingUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserAddressById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found.")
        );
        AddressDto address = userProducerAddress.getAddressById(user.getAddresses_id());
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setAddressFromDto(address);

        UserResponseDto UserResponseDto = new UserResponseDto();
        UserResponseDto.setAddress(addressResponseDto);
        UserResponseDto.setUserFromDto(user);

        return UserResponseDto;
    }
}