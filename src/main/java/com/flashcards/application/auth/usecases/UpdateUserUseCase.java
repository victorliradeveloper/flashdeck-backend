package com.flashcards.application.auth.usecases;

import com.flashcards.application.auth.dto.UpdateNameRequest;
import com.flashcards.application.auth.dto.UserProfileResponse;
import com.flashcards.domain.auth.entity.User;

/**
 * Interface do caso de uso para atualizar dados do usuário.
 */
public interface UpdateUserUseCase {
    /**
     * Atualiza o nome do usuário autenticado.
     * 
     * @param user usuário autenticado
     * @param request dados de atualização
     * @return dados atualizados do usuário
     */
    UserProfileResponse execute(User user, UpdateNameRequest request);
}
