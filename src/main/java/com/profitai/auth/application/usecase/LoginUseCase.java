package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.LoginRequest;
import com.profitai.auth.application.dto.LoginResponse;

/**
 * Interface do caso de uso de login. Define o contrato para autenticaÃ§Ã£o de
 * usuÃ¡rios.
 */
public interface LoginUseCase {
	/**
	 * Executa o caso de uso de login.
	 * 
	 * @param request
	 *            dados de login (email e senha)
	 * @return resposta com token JWT e informaÃ§Ãµes do usuÃ¡rio
	 */
	LoginResponse execute(LoginRequest request);
}
