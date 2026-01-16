package com.flashcards.application.auth.usecases;

import com.flashcards.application.auth.dto.UserProfileResponse;
import com.flashcards.domain.auth.entity.User;

/**
 * Interface do caso de uso para obter perfil do usuário autenticado.
 */
public interface GetUserProfileUseCase {
    /**
     * Obtém o perfil do usuário autenticado.
     * 
     * @param user usuário autenticado
     * @return dados do perfil do usuário
     */
    UserProfileResponse execute(User user);
}
