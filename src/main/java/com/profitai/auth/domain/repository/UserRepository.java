package com.profitai.auth.domain.repository;

import com.profitai.auth.domain.entity.User;
import com.profitai.auth.domain.valueobject.Email;
import java.util.Optional;

public interface UserRepository {
	Optional<User> findByEmail(Email email);
	Optional<User> findById(String id);
	User save(User user);
	boolean existsByEmail(Email email);
}
