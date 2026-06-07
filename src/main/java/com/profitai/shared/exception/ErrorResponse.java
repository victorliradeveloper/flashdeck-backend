package com.profitai.shared.exception;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, int status, String errorCode, String message, String path, String method,
		String requestId) {
}
