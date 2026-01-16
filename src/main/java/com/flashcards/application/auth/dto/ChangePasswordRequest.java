package com.flashcards.application.auth.dto;

public record ChangePasswordRequest(String currentPassword, String newPassword) {
}
