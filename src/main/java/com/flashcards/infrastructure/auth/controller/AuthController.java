package com.flashcards.infrastructure.auth.controller;

import com.flashcards.application.auth.dto.LoginRequest;
import com.flashcards.application.auth.dto.LoginResponse;
import com.flashcards.application.auth.dto.RegisterRequest;
import com.flashcards.application.auth.usecases.LoginUseCase;
import com.flashcards.application.auth.usecases.RegisterUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    
    private final LoginUseCase loginUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    
    public AuthController(LoginUseCase loginUseCase, RegisterUserUseCase registerUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUserUseCase = registerUserUseCase;
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
}

