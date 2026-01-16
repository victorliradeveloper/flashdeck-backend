package com.flashcards.infrastructure.auth.controller;

import com.flashcards.application.auth.dto.ChangePasswordRequest;
import com.flashcards.application.auth.dto.LoginRequest;
import com.flashcards.application.auth.dto.LoginResponse;
import com.flashcards.application.auth.dto.RegisterRequest;
import com.flashcards.application.auth.dto.UpdateNameRequest;
import com.flashcards.application.auth.dto.UserProfileResponse;
import com.flashcards.application.auth.usecases.ChangePasswordUseCase;
import com.flashcards.application.auth.usecases.GetUserProfileUseCase;
import com.flashcards.application.auth.usecases.LoginUseCase;
import com.flashcards.application.auth.usecases.RegisterUserUseCase;
import com.flashcards.application.auth.usecases.UpdateUserUseCase;
import com.flashcards.domain.auth.entity.User;
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
    private final ChangePasswordUseCase changePasswordUseCase;
    
    public AuthController(
            LoginUseCase loginUseCase,
            RegisterUserUseCase registerUserUseCase,
            GetUserProfileUseCase getUserProfileUseCase,
            UpdateUserUseCase updateUserUseCase,
            ChangePasswordUseCase changePasswordUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.updateUserUseCase = updateUserUseCase;
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

