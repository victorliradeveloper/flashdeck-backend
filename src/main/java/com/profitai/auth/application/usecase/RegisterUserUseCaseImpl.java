package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.RegisterRequest;
import com.profitai.auth.application.dto.LoginResponse;
import com.profitai.auth.application.mapper.UserMapper;
import com.profitai.auth.domain.entity.User;
import com.profitai.auth.domain.exception.UserAlreadyExistsException;
import com.profitai.auth.domain.repository.UserRepository;
import com.profitai.auth.domain.port.PasswordEncoder;
import com.profitai.auth.domain.port.TokenProvider;
import com.profitai.auth.domain.valueobject.Email;
import com.profitai.auth.domain.valueobject.Password;

/**
 * ImplementaÃ§Ã£o do caso de uso de registro de usuÃ¡rio. Esta classe nÃ£o usa
 * frameworks, apenas lÃ³gica de negÃ³cio.
 */
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	public RegisterUserUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			TokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public LoginResponse execute(RegisterRequest request) {
		Email email = Email.of(request.email());

		if (userRepository.existsByEmail(email)) {
			throw new UserAlreadyExistsException("User already exists with email: " + request.email());
		}

		Password password = Password.fromPlainText(request.password(), passwordEncoder);
		User user = User.create(request.name(), email, password);

		User savedUser = userRepository.save(user);

		String token = tokenProvider.generateToken(savedUser);

		return UserMapper.toLoginResponse(savedUser, token);
	}
}
