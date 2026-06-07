package com.profitai.auth.domain.valueobject;

import com.profitai.auth.domain.exception.InvalidEmailException;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Email {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

	private final String value;

	private Email(String value) {
		if (value == null || value.isBlank()) {
			throw new InvalidEmailException("Email cannot be null or empty");
		}

		String trimmedValue = value.trim().toLowerCase();
		if (!EMAIL_PATTERN.matcher(trimmedValue).matches()) {
			throw new InvalidEmailException("Invalid email format: " + value);
		}

		this.value = trimmedValue;
	}

	public static Email of(String value) {
		return new Email(value);
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Email email = (Email) o;
		return Objects.equals(value, email.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return value;
	}
}
