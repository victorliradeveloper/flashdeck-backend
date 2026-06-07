package com.profitai.auth.domain.port;

import com.profitai.auth.domain.entity.User;

/**
 * Porta (interface) para geraÃ§Ã£o e validaÃ§Ã£o de tokens JWT no domÃ­nio.
 * ImplementaÃ§Ãµes devem ser fornecidas pela camada de infraestrutura.
 * 
 * Esta Ã© uma porta outbound (outbound port) que permite ao domÃ­nio usar
 * serviÃ§os de infraestrutura sem depender diretamente deles.
 */
public interface TokenProvider {
	/**
	 * Gera um token JWT para o usuÃ¡rio.
	 * 
	 * @param user
	 *            usuÃ¡rio para o qual o token serÃ¡ gerado
	 * @return token JWT como string
	 */
	String generateToken(User user);

	/**
	 * Valida um token JWT e retorna o email do usuÃ¡rio.
	 * 
	 * @param token
	 *            token JWT a ser validado
	 * @return email do usuÃ¡rio se o token for vÃ¡lido, null caso contrÃ¡rio
	 */
	String validateToken(String token);

	/**
	 * Extrai o ID do usuÃ¡rio de um token JWT.
	 * 
	 * @param token
	 *            token JWT
	 * @return ID do usuÃ¡rio se o token for vÃ¡lido, null caso contrÃ¡rio
	 */
	String getUserIdFromToken(String token);
}
