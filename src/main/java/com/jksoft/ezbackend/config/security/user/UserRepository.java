package com.jksoft.ezbackend.config.security.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findUserByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findUserByActivationKey(UUID activationKey);
}
