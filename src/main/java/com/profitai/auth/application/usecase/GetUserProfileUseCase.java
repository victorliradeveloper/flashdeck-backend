package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.UserProfileResponse;
import com.profitai.auth.domain.entity.User;

/**
 * Interface do caso de uso para obter perfil do usuÃ¡rio autenticado.
 */
public interface GetUserProfileUseCase {
	/**
	 * ObtÃ©m o perfil do usuÃ¡rio autenticado.
	 * 
	 * @param user
	 *            usuÃ¡rio autenticado
	 * @return dados do perfil do usuÃ¡rio
	 */
	UserProfileResponse execute(User user);
}
