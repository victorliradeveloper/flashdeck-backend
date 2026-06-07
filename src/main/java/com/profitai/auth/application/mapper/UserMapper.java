package com.profitai.auth.application.mapper;

import com.profitai.auth.application.dto.LoginResponse;
import com.profitai.auth.application.dto.UserProfileResponse;
import com.profitai.auth.domain.entity.User;

public class UserMapper {

	public static LoginResponse toLoginResponse(User user, String token) {
		return new LoginResponse(user.getName(), token);
	}

	public static UserProfileResponse toUserProfileResponse(User user) {
		return new UserProfileResponse(user.getId(), user.getName(), user.getEmail().getValue(), user.getAvatarKey());
	}
}
