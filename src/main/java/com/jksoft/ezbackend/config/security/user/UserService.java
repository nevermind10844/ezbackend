package com.jksoft.ezbackend.config.security.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public void createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public User read(String username) throws NoSuchElementException{
		Optional<User> potUser = userRepository.findUserByUsername(username);
		if(potUser.isPresent())
			return potUser.get();
		else {
			potUser = userRepository.findByEmail(username);
			if(potUser.isPresent()) {
				return potUser.get();
			} else {
				throw new NoSuchElementException("No user found for username " + username);
			}
		}
	}
	
	public User read(UUID activationKey) throws NoSuchElementException{
		List<User> userList = userRepository.findAll();
		Optional<User> optUser = userList.stream().filter(u -> u.getActivationKey() != null && u.getActivationKey().toString().equals(activationKey.toString())).findFirst();
		return optUser.get();
	}
	
	public User update(User user) {
		User u = userRepository.getReferenceById(user.getId());
		u.setActive(user.isActive());
		return userRepository.save(u);
	}

	public List<User> list() {
		return userRepository.findAll();
	}
}
