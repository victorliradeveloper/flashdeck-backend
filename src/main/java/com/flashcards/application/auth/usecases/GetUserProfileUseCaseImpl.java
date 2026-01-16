package com.flashcards.application.auth.usecases;

import com.flashcards.application.auth.dto.UserProfileResponse;
import com.flashcards.application.auth.mapper.UserMapper;
import com.flashcards.domain.auth.entity.User;

/**
 * Implementação do caso de uso para obter perfil do usuário.
 */
public class GetUserProfileUseCaseImpl implements GetUserProfileUseCase {
    
    @Override
    public UserProfileResponse execute(User user) {
        return UserMapper.toUserProfileResponse(user);
    }
}
