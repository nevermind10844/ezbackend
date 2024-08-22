package com.jksoft.ezbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jksoft.ezbackend.config.security.user.CustomUserDetails;
import com.jksoft.ezbackend.config.security.user.User;
import com.jksoft.ezbackend.config.security.user.UserService;
import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Item;
import com.jksoft.ezbackend.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemService;

	@GetMapping("/admin/item/{itemId}")
	public String getItem(Model model, @PathVariable Long itemId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails cud = (CustomUserDetails) principal;
		User user = userService.read(cud.getId());
		Company company = user.getCompany();
		
		Item item = itemService.readItem(company, itemId);
		model.addAttribute("item", item);
		
		return "item/admin/itemDetails";
		
	}
}
