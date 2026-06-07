package com.profitai.auth.infrastructure.security;

import com.profitai.auth.domain.port.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Adaptador que implementa a interface PasswordEncoder do domÃ­nio usando a
 * implementaÃ§Ã£o do Spring Security.
 */
@Component
public class SpringPasswordEncoderAdapter implements PasswordEncoder {

	private final org.springframework.security.crypto.password.PasswordEncoder springPasswordEncoder;

	public SpringPasswordEncoderAdapter(
			org.springframework.security.crypto.password.PasswordEncoder springPasswordEncoder) {
		this.springPasswordEncoder = springPasswordEncoder;
	}

	@Override
	public String encode(String plainPassword) {
		return springPasswordEncoder.encode(plainPassword);
	}

	@Override
	public boolean matches(String plainPassword, String hashedPassword) {
		return springPasswordEncoder.matches(plainPassword, hashedPassword);
	}
}
