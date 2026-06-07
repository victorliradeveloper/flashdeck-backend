package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.LoginRequest;
import com.profitai.auth.application.dto.LoginResponse;
import com.profitai.auth.application.mapper.UserMapper;
import com.profitai.auth.domain.entity.User;
import com.profitai.auth.domain.exception.InvalidCredentialsException;
import com.profitai.auth.domain.exception.UserNotFoundException;
import com.profitai.auth.domain.repository.UserRepository;
import com.profitai.auth.domain.port.PasswordEncoder;
import com.profitai.auth.domain.port.TokenProvider;
import com.profitai.auth.domain.valueobject.Email;

/**
 * ImplementaÃ§Ã£o do caso de uso de login. Esta classe nÃ£o usa frameworks,
 * apenas lÃ³gica de negÃ³cio.
 */
public class LoginUseCaseImpl implements LoginUseCase {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	public LoginUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			TokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public LoginResponse execute(LoginRequest request) {
		Email email = Email.of(request.email());

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.email()));

		if (!user.verifyPassword(request.password(), passwordEncoder)) {
			throw new InvalidCredentialsException("Invalid password");
		}

		String token = tokenProvider.generateToken(user);

		return UserMapper.toLoginResponse(user, token);
	}
}
