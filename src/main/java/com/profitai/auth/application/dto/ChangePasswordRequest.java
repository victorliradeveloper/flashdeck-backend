package com.profitai.auth.application.dto;

public record ChangePasswordRequest(String currentPassword, String newPassword) {
}
