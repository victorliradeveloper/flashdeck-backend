package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.RegisterRequest;
import com.profitai.auth.application.dto.LoginResponse;

/**
 * Interface do caso de uso de registro de usuÃ¡rio. Define o contrato para
 * criaÃ§Ã£o de novos usuÃ¡rios.
 */
public interface RegisterUserUseCase {
	/**
	 * Executa o caso de uso de registro de usuÃ¡rio.
	 * 
	 * @param request
	 *            dados de registro (nome, email e senha)
	 * @return resposta com token JWT e informaÃ§Ãµes do usuÃ¡rio criado
	 */
	LoginResponse execute(RegisterRequest request);
}
