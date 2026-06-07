package com.profitai.auth.domain.entity;

import com.profitai.auth.domain.port.PasswordEncoder;
import com.profitai.auth.domain.valueobject.Email;
import com.profitai.auth.domain.valueobject.Password;
import java.util.Objects;
import java.util.UUID;

public class User {
	private String id;
	private String name;
	private Email email;
	private Password password;
	private String avatarKey;

	private User() {
		// Private constructor for frameworks
	}

	private User(String id, String name, Email email, Password password, String avatarKey) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.avatarKey = avatarKey;
	}

	public static User create(String name, Email email, Password password) {
		return new User(UUID.randomUUID().toString(), name, email, password, null);
	}

	public static User restore(String id, String name, Email email, Password password) {
		return new User(id, name, email, password, null);
	}

	public static User restore(String id, String name, Email email, Password password, String avatarKey) {
		return new User(id, name, email, password, avatarKey);
	}

	public void changePassword(Password newPassword) {
		this.password = newPassword;
	}

	public void updateName(String newName) {
		if (newName == null || newName.isBlank()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = newName.trim();
	}

	public boolean verifyPassword(String plainText, PasswordEncoder encoder) {
		return password.matches(plainText, encoder);
	}

	public void updateAvatarKey(String newAvatarKey) {
		if (newAvatarKey != null && newAvatarKey.isBlank()) {
			throw new IllegalArgumentException("Avatar key cannot be blank");
		}
		this.avatarKey = (newAvatarKey == null) ? null : newAvatarKey.trim();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Email getEmail() {
		return email;
	}

	public Password getPassword() {
		return password;
	}

	public String getAvatarKey() {
		return avatarKey;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
