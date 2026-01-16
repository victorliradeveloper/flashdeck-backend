package com.flashcards.application.auth.mapper;

import com.flashcards.application.auth.dto.LoginResponse;
import com.flashcards.application.auth.dto.UserProfileResponse;
import com.flashcards.domain.auth.entity.User;

public class UserMapper {
    
    public static LoginResponse toLoginResponse(User user, String token) {
        return new LoginResponse(user.getName(), token);
    }
    
    public static UserProfileResponse toUserProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail().getValue()
        );
    }
}

