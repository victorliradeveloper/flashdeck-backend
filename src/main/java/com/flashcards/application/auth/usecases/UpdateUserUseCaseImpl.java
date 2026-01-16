package com.flashcards.application.auth.usecases;

import com.flashcards.application.auth.dto.UpdateNameRequest;
import com.flashcards.application.auth.dto.UserProfileResponse;
import com.flashcards.application.auth.mapper.UserMapper;
import com.flashcards.domain.auth.entity.User;
import com.flashcards.domain.auth.repository.UserRepository;

/**
 * Implementação do caso de uso para atualizar dados do usuário.
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
