package com.jksoft.ezbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jksoft.ezbackend.config.security.user.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;

	@GetMapping("/")
	public String getHome(Model model) {
		model.addAttribute("showSetup", this.userService.list().size() <= 0);
		return "home";
	}
}
