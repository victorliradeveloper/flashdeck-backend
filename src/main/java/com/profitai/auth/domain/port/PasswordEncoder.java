package com.profitai.auth.domain.port;

/**
 * Porta (interface) para encoding de senhas no domÃ­nio. ImplementaÃ§Ãµes devem
 * ser fornecidas pela camada de infraestrutura.
 * 
 * Esta Ã© uma porta outbound (outbound port) que permite ao domÃ­nio usar
 * serviÃ§os de infraestrutura sem depender diretamente deles.
 */
public interface PasswordEncoder {
	/**
	 * Codifica uma senha em texto plano.
	 * 
	 * @param plainPassword
	 *            senha em texto plano
	 * @return senha codificada (hash)
	 */
	String encode(String plainPassword);

	/**
	 * Verifica se uma senha em texto plano corresponde ao hash.
	 * 
	 * @param plainPassword
	 *            senha em texto plano
	 * @param hashedPassword
	 *            senha codificada (hash)
	 * @return true se a senha corresponde ao hash
	 */
	boolean matches(String plainPassword, String hashedPassword);
}
