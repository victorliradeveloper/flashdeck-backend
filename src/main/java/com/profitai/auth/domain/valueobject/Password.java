package com.profitai.auth.domain.valueobject;

import com.profitai.auth.domain.port.PasswordEncoder;
import java.util.Objects;

public final class Password {
	private static final int MIN_LENGTH = 6;

	private final String hashedValue;

	private Password(String hashedValue) {
		if (hashedValue == null || hashedValue.isBlank()) {
			throw new IllegalArgumentException("Password hash cannot be null or empty");
		}
		this.hashedValue = hashedValue;
	}

	public static Password fromHashed(String hashedValue) {
		return new Password(hashedValue);
	}

	public static Password fromPlainText(String plainText, PasswordEncoder encoder) {
		if (plainText == null || plainText.isBlank()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}
		if (plainText.length() < MIN_LENGTH) {
			throw new IllegalArgumentException("Password must be at least " + MIN_LENGTH + " characters long");
		}
		return new Password(encoder.encode(plainText));
	}

	public String getHashedValue() {
		return hashedValue;
	}

	public boolean matches(String plainText, PasswordEncoder encoder) {
		return encoder.matches(plainText, hashedValue);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Password password = (Password) o;
		return Objects.equals(hashedValue, password.hashedValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(hashedValue);
	}
}
