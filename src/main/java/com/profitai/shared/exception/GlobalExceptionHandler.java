package com.profitai.shared.exception;

import com.profitai.auth.domain.exception.InvalidCredentialsException;
import com.profitai.auth.domain.exception.InvalidEmailException;
import com.profitai.auth.domain.exception.UserAlreadyExistsException;
import com.profitai.auth.domain.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private ErrorResponse buildErrorBody(HttpStatus status, String errorCode, String message,
			HttpServletRequest request) {
		String requestId = requestIdFrom(request);
		return new ErrorResponse(Instant.now(), status.value(), errorCode, message, request.getRequestURI(),
				request.getMethod(), requestId);
	}

	private String requestIdFrom(HttpServletRequest request) {
		String requestId = request.getHeader("X-Request-Id");
		if (!StringUtils.hasText(requestId)) {
			requestId = request.getHeader("X-Correlation-Id");
		}
		if (!StringUtils.hasText(requestId)) {
			requestId = UUID.randomUUID().toString();
		}
		return requestId;
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status).body(buildErrorBody(status, "USER_NOT_FOUND", ex.getMessage(), request));
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		return ResponseEntity.status(status)
				.body(buildErrorBody(status, "USER_ALREADY_EXISTS", ex.getMessage(), request));
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status)
				.body(buildErrorBody(status, "INVALID_CREDENTIALS", ex.getMessage(), request));
	}

	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ErrorResponse> handleInvalidEmailException(InvalidEmailException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(buildErrorBody(status, "INVALID_EMAIL", ex.getMessage(), request));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(buildErrorBody(status, "BAD_REQUEST", ex.getMessage(), request));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
		// Log full details server-side; return a safe, consistent payload to clients.
		String requestId = requestIdFrom(request);
		log.error("Unhandled exception (requestId={}, method={}, path={})", requestId, request.getMethod(),
				request.getRequestURI(), ex);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return ResponseEntity.status(status)
				.body(new ErrorResponse(Instant.now(), status.value(), "INTERNAL_SERVER_ERROR",
						"An unexpected error occurred.", request.getRequestURI(), request.getMethod(), requestId));
	}
}
