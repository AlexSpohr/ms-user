package com.compassuol.sp.challenge.msuser.web.dto.mapper;

import com.compassuol.sp.challenge.msuser.domain.user.entity.User;
import com.compassuol.sp.challenge.msuser.web.dto.LoginCreateDto;
import com.compassuol.sp.challenge.msuser.web.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMapper {

    public static User toLogin(LoginCreateDto createDto){
        return new ModelMapper().map(createDto, User.class);
    }
}
