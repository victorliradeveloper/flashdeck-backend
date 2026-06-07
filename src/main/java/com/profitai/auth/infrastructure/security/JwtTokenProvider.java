package com.profitai.auth.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.profitai.auth.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JwtTokenProvider {

	@Value("${api.security.token.secret}")
	private String secret;

	@Value("${api.security.token.expiration-hours:2}")
	private int expirationHours;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.create().withIssuer("profitai-api").withSubject(user.getEmail().getValue())
					.withClaim("userId", user.getId()).withClaim("name", user.getName())
					.withExpiresAt(generateExpirationDate()).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while generating token", exception);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("profitai-api").build().verify(token).getSubject();
		} catch (JWTVerificationException exception) {
			return null;
		}
	}

	public String getUserIdFromToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("profitai-api").build().verify(token).getClaim("userId")
					.asString();
		} catch (JWTVerificationException exception) {
			return null;
		}
	}

	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(expirationHours).toInstant(ZoneOffset.UTC);
	}
}
