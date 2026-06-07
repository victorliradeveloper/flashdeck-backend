package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.UserProfileResponse;
import com.profitai.auth.application.mapper.UserMapper;
import com.profitai.auth.domain.entity.User;

/**
 * ImplementaÃ§Ã£o do caso de uso para obter perfil do usuÃ¡rio.
 */
public class GetUserProfileUseCaseImpl implements GetUserProfileUseCase {

	@Override
	public UserProfileResponse execute(User user) {
		return UserMapper.toUserProfileResponse(user);
	}
}
