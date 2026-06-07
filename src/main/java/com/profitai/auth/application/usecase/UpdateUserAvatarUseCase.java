package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.UpdateAvatarRequest;
import com.profitai.auth.application.dto.UserProfileResponse;
import com.profitai.auth.domain.entity.User;

public interface UpdateUserAvatarUseCase {
	UserProfileResponse execute(User user, UpdateAvatarRequest request);
}
