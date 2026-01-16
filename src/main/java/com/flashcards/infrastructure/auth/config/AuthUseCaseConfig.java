package com.flashcards.infrastructure.auth.config;

import com.flashcards.application.auth.usecases.ChangePasswordUseCase;
import com.flashcards.application.auth.usecases.ChangePasswordUseCaseImpl;
import com.flashcards.application.auth.usecases.GetUserProfileUseCase;
import com.flashcards.application.auth.usecases.GetUserProfileUseCaseImpl;
import com.flashcards.application.auth.usecases.LoginUseCase;
import com.flashcards.application.auth.usecases.LoginUseCaseImpl;
import com.flashcards.application.auth.usecases.RegisterUserUseCase;
import com.flashcards.application.auth.usecases.RegisterUserUseCaseImpl;
import com.flashcards.application.auth.usecases.UpdateUserUseCase;
import com.flashcards.application.auth.usecases.UpdateUserUseCaseImpl;
import com.flashcards.domain.auth.port.PasswordEncoder;
import com.flashcards.domain.auth.port.TokenProvider;
import com.flashcards.domain.auth.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para registrar os casos de uso de autenticação no Spring.
 * Esta classe faz a ponte entre a camada de aplicação (sem frameworks)
 * e a camada de infraestrutura (com Spring).
 */
@Configuration
public class AuthUseCaseConfig {
    
    @Bean
    public LoginUseCase loginUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenProvider tokenProvider) {
        return new LoginUseCaseImpl(userRepository, passwordEncoder, tokenProvider);
    }
    
    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
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
    public ChangePasswordUseCase changePasswordUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return new ChangePasswordUseCaseImpl(userRepository, passwordEncoder);
    }
}

