package com.jksoft.ezbackend.controller.advices;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.jksoft.ezbackend.config.security.user.User;
import com.jksoft.ezbackend.config.security.user.UserService;
import com.jksoft.ezbackend.entities.structure.Company;
import com.jksoft.ezbackend.entities.structure.Namespace;

@ControllerAdvice
public class NavigationAdivce {

	private final UserService userService;

	public NavigationAdivce(UserService userService) {
		this.userService = userService;
	}

	@ModelAttribute("namespaceList")
	public List<Namespace> getNamespaceList() {
		User user = this.userService.getCurrentUser();
		if (user != null && user.getCompany() != null) {
			Company company = user.getCompany();
			return company.getNamespaceList();
		} else {
			return null;
		}
	}
}
