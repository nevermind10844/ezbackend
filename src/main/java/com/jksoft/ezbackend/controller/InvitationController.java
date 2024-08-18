package com.jksoft.ezbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jksoft.ezbackend.config.security.user.User;
import com.jksoft.ezbackend.config.security.user.UserService;
import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Invitation;
import com.jksoft.ezbackend.service.CompanyService;
import com.jksoft.ezbackend.service.InvitationService;

@Controller
public class InvitationController {

	@Autowired
	InvitationService invitationService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	UserService userService;

	@GetMapping("/invitation")
	public String inviteActivation(Model model, @RequestParam("key") String invitationKey) {
		Invitation invitation = this.invitationService.readInvitation(invitationKey);
		
		String target;
		
		User user = new User();
		Company company = this.companyService.readCompany(invitation.getInvitationTarget());
		user.setEmail(invitation.getEmail());
		
		model.addAttribute("companyName", company.getName());
		model.addAttribute("newUser", user);
		model.addAttribute("invitation", invitation);

		switch (invitation.getInvitationType()) {
			case ADMIN_INVITATION:
				target = "company/company_signup";
				break;
			case USER_INVITATION:
				target = "company/company_signup";
				break;
			default:
				target = "stinking finger";
				break;
		}
		
		return target;
	}
	
	@PostMapping("/invitation")
	public String inviteCompletion(Model model, User user, @RequestParam String invitationKey) {
		Invitation invitation = this.invitationService.readInvitation(invitationKey);
		
		if(!user.getEmail().equals(invitation.getEmail()))
			return "hoecker, sie sind raus";
		
		String target;
		
		Company company = this.companyService.readCompany(invitation.getInvitationTarget());	
		user.setCompany(company);
		
		switch (invitation.getInvitationType()) {
			case ADMIN_INVITATION:
				user.setCompanyAdmin(true);
				target = "redirect:/admin/company";
				break;
			case USER_INVITATION:
				target = "redirect:/company";
				break;
			default:
				target = "stinking finger";
				break;
		}
		
		user.setActive(true);
		userService.createUser(user);
		
		return target;
	}
}
