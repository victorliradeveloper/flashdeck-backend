package com.profitai.auth.application.usecase;

import com.profitai.auth.application.dto.ChangePasswordRequest;
import com.profitai.auth.domain.entity.User;
import com.profitai.auth.domain.exception.InvalidCredentialsException;
import com.profitai.auth.domain.port.PasswordEncoder;
import com.profitai.auth.domain.repository.UserRepository;
import com.profitai.auth.domain.valueobject.Password;

/**
 * ImplementaÃ§Ã£o do caso de uso para alterar senha do usuÃ¡rio.
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
		// Verifica se a senha atual estÃ¡ correta
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
