package com.compassuol.sp.challenge.msuser.web.dto.mapper;

import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.compassuol.sp.challenge.msuser.web.dto.UserCreateDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserPasswordDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserResponseDto toDto(User user) {
        return new ModelMapper().map(user, UserResponseDto.class);
    }

    public static <T> User toEntity(T dto) {
        return new ModelMapper().map(dto, User.class);
    }
}
