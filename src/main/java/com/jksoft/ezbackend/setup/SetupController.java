package com.jksoft.ezbackend.setup;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jksoft.ezbackend.config.security.user.User;
import com.jksoft.ezbackend.config.security.user.UserService;

@Controller
public class SetupController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/setup")
	public String getSetup(Model model) {
		model.addAttribute("newUser", new User());
		return "setup/setup";
	}
	
	@PostMapping("/setup/signup")
	public String signup(RedirectAttributes redirectAttributes, User user) {
		List<User> setupUserList = userService.listSetupUser();
		if(setupUserList.size() <= 0) {
			User potUser = null;
			try {
				potUser = userService.read(user.getEmail());
			} catch (NoSuchElementException e) {
				System.out.println(String.format("Good thing: No user found for %s", user.getEmail()));
			}
			
			if (potUser == null) {
				user.setActivationKey(UUID.randomUUID());
				user.setActive(false);
				
				user.setSetupUser(true);
				user.setInstanceAdmin(true);
	
				userService.createUser(user);
				
				redirectAttributes.addFlashAttribute("activationKey", user.getActivationKey().toString());
			} else {
				System.out.println(String.format("not a good thing: user found for %s", user.getEmail()));
			}
			return "redirect:/setup";
		} else {
			System.out.println("system has already been setup!");
			return "redirect:/login";
		}
	}
}
