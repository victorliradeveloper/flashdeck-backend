package com.profitai.auth.infrastructure.config;

import com.profitai.auth.application.usecase.ChangePasswordUseCase;
import com.profitai.auth.application.usecase.ChangePasswordUseCaseImpl;
import com.profitai.auth.application.usecase.GetUserProfileUseCase;
import com.profitai.auth.application.usecase.GetUserProfileUseCaseImpl;
import com.profitai.auth.application.usecase.LoginUseCase;
import com.profitai.auth.application.usecase.LoginUseCaseImpl;
import com.profitai.auth.application.usecase.RegisterUserUseCase;
import com.profitai.auth.application.usecase.RegisterUserUseCaseImpl;
import com.profitai.auth.application.usecase.UpdateUserUseCase;
import com.profitai.auth.application.usecase.UpdateUserUseCaseImpl;
import com.profitai.auth.application.usecase.UpdateUserAvatarUseCase;
import com.profitai.auth.application.usecase.UpdateUserAvatarUseCaseImpl;
import com.profitai.auth.domain.port.PasswordEncoder;
import com.profitai.auth.domain.port.TokenProvider;
import com.profitai.auth.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ConfiguraÃ§Ã£o para registrar os casos de uso de autenticaÃ§Ã£o no Spring.
 * Esta classe faz a ponte entre a camada de aplicaÃ§Ã£o (sem frameworks) e a
 * camada de infraestrutura (com Spring).
 */
@Configuration
public class AuthUseCaseConfig {

	@Bean
	public LoginUseCase loginUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder,
			TokenProvider tokenProvider) {
		return new LoginUseCaseImpl(userRepository, passwordEncoder, tokenProvider);
	}

	@Bean
	public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder,
			TokenProvider tokenProvider) {
		return new RegisterUserUseCaseImpl(userRepository, passwordEncoder, tokenProvider);
	}

	@Bean
	public GetUserProfileUseCase getUserProfileUseCase() {
		return new GetUserProfileUseCaseImpl();
	}

	@Bean
	public UpdateUserUseCase updateUserUseCase(UserRepository userRepository) {
		return new UpdateUserUseCaseImpl(userRepository);
	}

	@Bean
	public UpdateUserAvatarUseCase updateUserAvatarUseCase(UserRepository userRepository) {
		return new UpdateUserAvatarUseCaseImpl(userRepository);
	}

	@Bean
	public ChangePasswordUseCase changePasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return new ChangePasswordUseCaseImpl(userRepository, passwordEncoder);
	}
}
