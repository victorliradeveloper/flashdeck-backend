package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.UpdateNameRequest;
import com.profitai.auth.application.dto.UserProfileResponse;
import com.profitai.auth.application.mapper.UserMapper;
import com.profitai.auth.domain.entity.User;
import com.profitai.auth.domain.repository.UserRepository;

/**
 * ImplementaÃ§Ã£o do caso de uso para atualizar dados do usuÃ¡rio.
 */
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

	private final UserRepository userRepository;

	public UpdateUserUseCaseImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserProfileResponse execute(User user, UpdateNameRequest request) {
		user.updateName(request.name());
		User updatedUser = userRepository.save(user);
		return UserMapper.toUserProfileResponse(updatedUser);
	}
}
