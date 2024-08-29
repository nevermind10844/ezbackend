package com.jksoft.ezbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jksoft.ezbackend.config.security.user.CustomUserDetails;
import com.jksoft.ezbackend.config.security.user.User;
import com.jksoft.ezbackend.config.security.user.UserService;
import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Item;
import com.jksoft.ezbackend.entities.Namespace;
import com.jksoft.ezbackend.error.BadRequestException;
import com.jksoft.ezbackend.error.StructureObjectNotFoundException;
import com.jksoft.ezbackend.service.ItemService;
import com.jksoft.ezbackend.service.NamespaceService;

@Controller
public class NamespaceController {

	@Autowired
	UserService userService;

	@Autowired
	NamespaceService namespaceService;

	@Autowired
	ItemService itemService;

	@PostMapping("/admin/namespace/{namespaceId}/item")
	public String createNamespaceItem(Model model, @PathVariable Long namespaceId, Item item) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails cud = (CustomUserDetails) principal;
		User user = userService.read(cud.getId());
		Company company = user.getCompany();
		Namespace namespace = namespaceService.readNamespace(company, namespaceId);

		item.setCompany(company);
		item.setNamespace(namespace);
		itemService.createItem(item);

		return String.format("redirect:/admin/namespace/%d", namespaceId);
	}

	@GetMapping("/admin/namespace/{namespaceId}")
	public String getAdminNamespace(Model model, @PathVariable Long namespaceId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails cud = (CustomUserDetails) principal;
		User user = userService.read(cud.getId());
		Company company = user.getCompany();

		model.addAttribute("namespace", namespaceService.readNamespace(company, namespaceId));

		model.addAttribute("newItem", new Item());

		return "structure/namespace/admin/namespaceDetails";
	}

	@PostMapping("/admin/namespace/{namespaceId}")
	public String updateAdminNamespace(Model model, @PathVariable Long namespaceId, Namespace namespace) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails cud = (CustomUserDetails) principal;
		User user = userService.read(cud.getId());
		Company company = user.getCompany();

		if (!namespaceId.equals(namespace.getId()))
			throw new BadRequestException();
		else {
			Namespace remoteNamespace = namespaceService.readNamespace(company, namespaceId);
			if(remoteNamespace == null)
				throw new StructureObjectNotFoundException();
			else {
				namespace.setCompany(company);
				namespace.setCreated(remoteNamespace.getCreated());
				namespaceService.updateNamespace(namespace);
			}
		}

		return String.format("redirect:/admin/namespace/%d", namespaceId);
	}
}
