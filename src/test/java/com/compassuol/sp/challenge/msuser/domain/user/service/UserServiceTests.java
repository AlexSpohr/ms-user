package com.compassuol.sp.challenge.msuser.domain.user.service;

import com.compassuol.sp.challenge.msuser.common.UserConstants;
import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.compassuol.sp.challenge.msuser.domain.user.repository.UserRepository;
import com.compassuol.sp.challenge.msuser.web.dto.AddressDto;
import com.compassuol.sp.challenge.msuser.web.dto.AddressResponseDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserResponseDto;
import com.compassuol.sp.challenge.msuser.web.producer.UserProducerAddress;
import com.compassuol.sp.challenge.msuser.web.producer.UserProducerNotification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static com.compassuol.sp.challenge.msuser.common.UserConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTests{

    @Mock
    UserRepository userRepository;

    @Mock
    UserProducerAddress userProducerAddress;

    @Mock
    UserProducerNotification userProducerNotification;

    @InjectMocks
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    public void createUser_WithValidData_ReturnsUser() {
        when(passwordEncoder.encode(any())).thenReturn("$10$kYigVdzsoy8WRPaSBH4j.uYgnT5LhTTFSB.UmNfGvNnLYCk/lQXt.");
        when(userProducerAddress.saveAddress(any(), any())).thenReturn(ADDRESS_DTO);
        when(userRepository.save(any())).thenReturn(USER_CONSTANT);

        doNothing().when(userProducerNotification).send(any());

        UserResponseDto userResponseDto = userService.createUser(USER_CREATE_DTO);
        userResponseDto.setId(1L);
        UserResponseDto expectedUserResponse = mockUserResponseDTO(UserConstants.USER_CONSTANT, ADDRESS_CREATE);

        assertEquals(userResponseDto, expectedUserResponse);
    }

    public static UserResponseDto mockUserResponseDTO(User user, AddressResponseDto address) {
        return new UserResponseDto(
                user.getId(),
                address,
                user.getFirstName(),
                user.getLastName(),
                user.getCpf(),
                user.getBirthdate(),
                user.getEmail(),
                user.getActive());
    }

    @Test
    public void getUserById_WithValidId_ReturnsUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        user.setFirstName("Gustavo");
        user.setLastName("Silva");
        user.setCpf("725.598.470-35");
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdate = dateFormat.parse("2000-04-04");
            user.setBirthdate(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setEmail("gustavo@email.com");
        user.setActive(true);

        UserResponseDto expectedUserResponse = mockUserResponseDTO(UserConstants.USER_CONSTANT, ADDRESS_CREATE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User userEntity = userService.getUserById(userId);

        UserResponseDto actualUserResponse = new UserResponseDto();
        actualUserResponse.setId(userEntity.getId());
        actualUserResponse.setFirstName(userEntity.getFirstName());
        actualUserResponse.setLastName(userEntity.getLastName());
        actualUserResponse.setCpf(userEntity.getCpf());
        actualUserResponse.setBirthdate(userEntity.getBirthdate());
        actualUserResponse.setEmail(userEntity.getEmail());
        actualUserResponse.setActive(userEntity.getActive());
        actualUserResponse.setAddress(ADDRESS_CREATE);

        assertEquals(expectedUserResponse, actualUserResponse);
    }
}
