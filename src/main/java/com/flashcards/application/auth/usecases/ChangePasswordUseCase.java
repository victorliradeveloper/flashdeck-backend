package com.flashcards.application.auth.usecases;

import com.flashcards.application.auth.dto.ChangePasswordRequest;
import com.flashcards.domain.auth.entity.User;

/**
 * Interface do caso de uso para alterar senha do usuário.
 */
public interface ChangePasswordUseCase {
    /**
     * Altera a senha do usuário autenticado.
     * 
     * @param user usuário autenticado
     * @param request dados de alteração de senha
     */
    void execute(User user, ChangePasswordRequest request);
}
