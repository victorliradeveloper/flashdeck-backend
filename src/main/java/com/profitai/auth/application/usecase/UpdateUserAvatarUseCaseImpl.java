package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.UpdateAvatarRequest;
import com.profitai.auth.application.dto.UserProfileResponse;
import com.profitai.auth.application.mapper.UserMapper;
import com.profitai.auth.domain.entity.User;
import com.profitai.auth.domain.repository.UserRepository;

public class UpdateUserAvatarUseCaseImpl implements UpdateUserAvatarUseCase {

	private final UserRepository userRepository;

	public UpdateUserAvatarUseCaseImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserProfileResponse execute(User user, UpdateAvatarRequest request) {
		user.updateAvatarKey(request.avatarKey());
		User updatedUser = userRepository.save(user);
		return UserMapper.toUserProfileResponse(updatedUser);
	}
}
