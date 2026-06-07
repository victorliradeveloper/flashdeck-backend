package com.profitai.auth.infrastructure.web;

import com.profitai.auth.application.dto.ChangePasswordRequest;
import com.profitai.auth.application.dto.LoginRequest;
import com.profitai.auth.application.dto.LoginResponse;
import com.profitai.auth.application.dto.RegisterRequest;
import com.profitai.auth.application.dto.UpdateAvatarRequest;
import com.profitai.auth.application.dto.UpdateNameRequest;
import com.profitai.auth.application.dto.UserProfileResponse;
import com.profitai.auth.application.usecase.ChangePasswordUseCase;
import com.profitai.auth.application.usecase.GetUserProfileUseCase;
import com.profitai.auth.application.usecase.LoginUseCase;
import com.profitai.auth.application.usecase.RegisterUserUseCase;
import com.profitai.auth.application.usecase.UpdateUserAvatarUseCase;
import com.profitai.auth.application.usecase.UpdateUserUseCase;
import com.profitai.auth.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	private final LoginUseCase loginUseCase;
	private final RegisterUserUseCase registerUserUseCase;
	private final GetUserProfileUseCase getUserProfileUseCase;
	private final UpdateUserUseCase updateUserUseCase;
	private final UpdateUserAvatarUseCase updateUserAvatarUseCase;
	private final ChangePasswordUseCase changePasswordUseCase;

	public AuthController(LoginUseCase loginUseCase, RegisterUserUseCase registerUserUseCase,
			GetUserProfileUseCase getUserProfileUseCase, UpdateUserUseCase updateUserUseCase,
			UpdateUserAvatarUseCase updateUserAvatarUseCase, ChangePasswordUseCase changePasswordUseCase) {
		this.loginUseCase = loginUseCase;
		this.registerUserUseCase = registerUserUseCase;
		this.getUserProfileUseCase = getUserProfileUseCase;
		this.updateUserUseCase = updateUserUseCase;
		this.updateUserAvatarUseCase = updateUserAvatarUseCase;
		this.changePasswordUseCase = changePasswordUseCase;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse response = loginUseCase.execute(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
		LoginResponse response = registerUserUseCase.execute(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/profile")
	public ResponseEntity<UserProfileResponse> getProfile() {
		User user = getAuthenticatedUser();
		UserProfileResponse response = getUserProfileUseCase.execute(user);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/profile")
	public ResponseEntity<UserProfileResponse> updateProfile(@RequestBody UpdateNameRequest request) {
		User user = getAuthenticatedUser();
		UserProfileResponse response = updateUserUseCase.execute(user, request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/profile/avatar")
	public ResponseEntity<UserProfileResponse> updateAvatar(@RequestBody UpdateAvatarRequest request) {
		User user = getAuthenticatedUser();
		UserProfileResponse response = updateUserAvatarUseCase.execute(user, request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/password")
	public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
		User user = getAuthenticatedUser();
		changePasswordUseCase.execute(user, request);
		return ResponseEntity.noContent().build();
	}

	private User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
			throw new IllegalStateException("User not authenticated");
		}
		return (User) authentication.getPrincipal();
	}
}
