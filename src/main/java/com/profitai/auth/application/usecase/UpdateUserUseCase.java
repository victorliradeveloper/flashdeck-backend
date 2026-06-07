package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.UpdateNameRequest;
import com.profitai.auth.application.dto.UserProfileResponse;
import com.profitai.auth.domain.entity.User;

/**
 * Interface do caso de uso para atualizar dados do usuÃ¡rio.
 */
public interface UpdateUserUseCase {
	/**
	 * Atualiza o nome do usuÃ¡rio autenticado.
	 * 
	 * @param user
	 *            usuÃ¡rio autenticado
	 * @param request
	 *            dados de atualizaÃ§Ã£o
	 * @return dados atualizados do usuÃ¡rio
	 */
	UserProfileResponse execute(User user, UpdateNameRequest request);
}
