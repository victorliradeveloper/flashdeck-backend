package com.profitai.auth.infrastructure.persistence.mapper;

import com.profitai.auth.domain.entity.User;
import com.profitai.auth.domain.valueobject.Email;
import com.profitai.auth.domain.valueobject.Password;
import com.profitai.auth.infrastructure.persistence.entity.UserEntity;

/**
 * Mapper responsÃ¡vel por converter entre entidades de domÃ­nio (User) e
 * entidades de persistÃªncia (UserEntity).
 */
public class UserEntityMapper {

	public UserEntity toEntity(User user) {
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setName(user.getName());
		entity.setEmail(user.getEmail().getValue());
		entity.setPassword(user.getPassword().getHashedValue());
		entity.setAvatarKey(user.getAvatarKey());
		return entity;
	}

	public User toDomain(UserEntity entity) {
		Email email = Email.of(entity.getEmail());
		Password password = Password.fromHashed(entity.getPassword());
		return User.restore(entity.getId(), entity.getName(), email, password, entity.getAvatarKey());
	}
}
