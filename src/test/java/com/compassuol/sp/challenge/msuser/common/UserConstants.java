package com.compassuol.sp.challenge.msuser.common;

import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.compassuol.sp.challenge.msuser.web.dto.AddressDto;
import com.compassuol.sp.challenge.msuser.web.dto.AddressResponseDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserCreateDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserResponseDto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserConstants {

    public static final UserCreateDto USER_CREATE_DTO;

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        USER_CREATE_DTO = new UserCreateDto();
        USER_CREATE_DTO.setFirstName("Gustavo");
        USER_CREATE_DTO.setLastName("Silva");
        USER_CREATE_DTO.setCpf("725.598.470-35");
        try {
            USER_CREATE_DTO.setBirthdate(sdf.parse("2000-04-04"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        USER_CREATE_DTO.setEmail("gustavo@email.com");
        USER_CREATE_DTO.setCep("01310-930");
        USER_CREATE_DTO.setPassword("12345678");
        USER_CREATE_DTO.setActive(true);
    }


    public static final AddressDto ADDRESS_DTO;

    static {
        ADDRESS_DTO = new AddressDto();
        ADDRESS_DTO.setId(1L);
        ADDRESS_DTO.setCep("01310-930");
        ADDRESS_DTO.setUf("SP");
        ADDRESS_DTO.setDdd(11);
        ADDRESS_DTO.setLocalidade("São Paulo");
        ADDRESS_DTO.setLogradouro("Avenida Paulista");
        ADDRESS_DTO.setComplemento("1234");
    }


    public static final AddressResponseDto ADDRESS_CREATE;

    static {
        ADDRESS_CREATE = new AddressResponseDto();
        ADDRESS_CREATE.setCep("01310-930");
        ADDRESS_CREATE.setUf("SP");
        ADDRESS_CREATE.setDdd(11);
        ADDRESS_CREATE.setLocalidade("São Paulo");
        ADDRESS_CREATE.setLogradouro("Avenida Paulista");
        ADDRESS_CREATE.setComplemento("1234");
    }

    public static final UserResponseDto USER_RESPONSE;

    static {
        USER_RESPONSE = new UserResponseDto();
        USER_RESPONSE.setId(1L);
        USER_RESPONSE.setAddress(ADDRESS_CREATE);
        USER_RESPONSE.setFirstName("Gustavo");
        USER_RESPONSE.setLastName("Silva");
        USER_RESPONSE.setCpf("725.598.470-35");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse("2000-04-04");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        USER_RESPONSE.setBirthdate(date);
        USER_RESPONSE.setEmail("gustavo@email.com");
        USER_RESPONSE.setActive(true);
    }

    public static final UserCreateDto EXISTING_USER_CREATE_DTO;

    static {
        EXISTING_USER_CREATE_DTO = new UserCreateDto();
        EXISTING_USER_CREATE_DTO.setFirstName("Gustavo");
        EXISTING_USER_CREATE_DTO.setLastName("Silva");
        EXISTING_USER_CREATE_DTO.setCpf("725.598.470-35");
        EXISTING_USER_CREATE_DTO.setEmail("gustavo@email.com");
    }

    public static final UserCreateDto CONFLICTING_USER_CREATE_DTO;

    static {
        CONFLICTING_USER_CREATE_DTO = new UserCreateDto();
        CONFLICTING_USER_CREATE_DTO.setFirstName("Nome");
        CONFLICTING_USER_CREATE_DTO.setLastName("Sobrenome");
        CONFLICTING_USER_CREATE_DTO.setCpf("589.410.480-79");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2000-04-04");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        CONFLICTING_USER_CREATE_DTO.setBirthdate(date);
        CONFLICTING_USER_CREATE_DTO.setEmail("teste16@email.com");
        CONFLICTING_USER_CREATE_DTO.setCep("98910-000");
        CONFLICTING_USER_CREATE_DTO.setPassword("12345678");
        CONFLICTING_USER_CREATE_DTO.setActive(true);
    }

    public static final String USER_EMAIL = "gustavo@email.com";
    public static final String USER_PASSWORD = "12345678";

    public static final User USER_CONSTANT;

    static {
        USER_CONSTANT = new User();
        USER_CONSTANT.setId(1L);
        USER_CONSTANT.setAddresses_id(123456789L);
        USER_CONSTANT.setFirstName("Gustavo");
        USER_CONSTANT.setLastName("Silva");
        USER_CONSTANT.setCpf("725.598.470-35");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse("2000-04-04");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        USER_CONSTANT.setBirthdate(date);
        USER_CONSTANT.setEmail("gustavo@email.com");
        USER_CONSTANT.setPassword("$10$kYigVdzsoy8WRPaSBH4j.uYgnT5LhTTFSB.UmNfGvNnLYCk/lQXt."); // Senha encriptada
        USER_CONSTANT.setActive(true);
    }
}
