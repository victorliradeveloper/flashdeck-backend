package com.flashcards.application.auth.usecases;

import com.flashcards.application.auth.dto.ChangePasswordRequest;
import com.flashcards.domain.auth.entity.User;
import com.flashcards.domain.auth.exception.InvalidCredentialsException;
import com.flashcards.domain.auth.port.PasswordEncoder;
import com.flashcards.domain.auth.repository.UserRepository;
import com.flashcards.domain.auth.valueobject.Password;

/**
 * Implementação do caso de uso para alterar senha do usuário.
 */
public class ChangePasswordUseCaseImpl implements ChangePasswordUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public ChangePasswordUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void execute(User user, ChangePasswordRequest request) {
        // Verifica se a senha atual está correta
        if (!user.verifyPassword(request.currentPassword(), passwordEncoder)) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }
        
        // Cria nova senha criptografada
        Password newPassword = Password.fromPlainText(request.newPassword(), passwordEncoder);
        
        // Atualiza a senha
        user.changePassword(newPassword);
        
        // Salva no banco
        userRepository.save(user);
    }
}
