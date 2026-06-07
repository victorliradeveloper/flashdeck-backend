package com.profitai.auth.infrastructure.security;

import com.profitai.auth.domain.entity.User;
import com.profitai.auth.domain.port.TokenProvider;
import org.springframework.stereotype.Component;

/**
 * Adaptador que implementa a interface TokenProvider do domÃ­nio usando a
 * implementaÃ§Ã£o JWT da infraestrutura.
 */
@Component
public class JwtTokenProviderAdapter implements TokenProvider {

	private final JwtTokenProvider jwtTokenProvider;

	public JwtTokenProviderAdapter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String generateToken(User user) {
		return jwtTokenProvider.generateToken(user);
	}

	@Override
	public String validateToken(String token) {
		return jwtTokenProvider.validateToken(token);
	}

	@Override
	public String getUserIdFromToken(String token) {
		return jwtTokenProvider.getUserIdFromToken(token);
	}
}
