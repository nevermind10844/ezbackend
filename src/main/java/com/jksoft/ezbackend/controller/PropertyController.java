package com.jksoft.ezbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.jksoft.ezbackend.config.security.user.CustomUserDetails;
import com.jksoft.ezbackend.config.security.user.User;
import com.jksoft.ezbackend.config.security.user.UserService;
import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Item;
import com.jksoft.ezbackend.entities.Property;
import com.jksoft.ezbackend.entities.PropertyType;
import com.jksoft.ezbackend.service.ItemService;
import com.jksoft.ezbackend.service.PropertyService;

@Controller
public class PropertyController {
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	PropertyService propertyService;

	@GetMapping("/admin/property/{propertyId}")
	public String getProperty(Model model, @PathVariable Long propertyId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails cud = (CustomUserDetails) principal;
		User user = userService.read(cud.getId());
		Company company = user.getCompany();
		
		Property property = propertyService.readProperty(company, propertyId);
		model.addAttribute("property", property);
		
		return "property/admin/propertyDetails";
		
	}
}
