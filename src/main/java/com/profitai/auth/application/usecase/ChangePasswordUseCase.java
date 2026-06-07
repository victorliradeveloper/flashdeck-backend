package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.ChangePasswordRequest;
import com.profitai.auth.domain.entity.User;

/**
 * Interface do caso de uso para alterar senha do usuÃ¡rio.
 */
public interface ChangePasswordUseCase {
	/**
	 * Altera a senha do usuÃ¡rio autenticado.
	 * 
	 * @param user
	 *            usuÃ¡rio autenticado
	 * @param request
	 *            dados de alteraÃ§Ã£o de senha
	 */
	void execute(User user, ChangePasswordRequest request);
}
