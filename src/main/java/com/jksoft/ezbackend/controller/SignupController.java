package com.jksoft.ezbackend.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jksoft.ezbackend.config.security.user.User;
import com.jksoft.ezbackend.config.security.user.UserService;

@Controller
public class SignupController {

	@Autowired
	UserService userService;
	
	@GetMapping(value="/signup")
	public String preSignup(Model model) {
		if(!model.containsAttribute("activationKey")) {
			model.addAttribute("newUser", new User());
		}
		return "user/signup";
	}

	@PostMapping(value = "/signup")
	public String signup(Model model, RedirectAttributes redirectAttributes, User user) {
		List<User> setuUserList = userService.listSetupUser();
		
		if(setuUserList.size() >= 1) {
			User potUser = null;
			try {
				potUser = userService.read(user.getEmail());
			} catch (NoSuchElementException e) {
				System.out.println(String.format("Good thing: No user found for %s", user.getEmail()));
			}
	
			if (potUser == null) {
				user.setActivationKey(UUID.randomUUID());
				user.setActive(false);
	
				userService.createUser(user);
				
				redirectAttributes.addFlashAttribute("activationKey", user.getActivationKey().toString());
			} else {
				System.out.println(String.format("not a good thing: user found for %s", user.getEmail()));
			}
		} else {
			System.out.println("uh oh... system has not yet been setup properly!");
		}
		return "redirect:/signup";
	}

	@GetMapping(value = "/signup/{activationKey}")
	public String activateUser(Model model, RedirectAttributes redirectAttributes, @PathVariable String activationKey) {
		UUID activationIdentifier = UUID.fromString(activationKey);
		String activationResult = "";
		User user = null;
		try {
			user = userService.read(activationIdentifier);
		} catch (NoSuchElementException ex) {

		}
		if (user.isActive()) {
			activationResult = "Benutzer bereits aktiviert";
		} else {
			user.setActive(true);
			userService.update(user);
			activationResult = "Benutzer erfolgreich aktiviert";
		}
		redirectAttributes.addFlashAttribute("activationResult", activationResult);

		return "redirect:/login";
	}
}
