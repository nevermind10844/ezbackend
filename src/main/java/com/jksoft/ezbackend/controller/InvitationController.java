package com.jksoft.ezbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jksoft.ezbackend.config.security.user.User;
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

	@GetMapping("/invitation")
	public String inviteActivation(Model model, @RequestParam("key") String invitationKey) {
		Invitation invitation = this.invitationService.readInvitation(invitationKey);
		
		String target;
		switch (invitation.getInvitationType()) {
			case ADMIN_INVITATION:
				Company company = this.companyService.readCompany(invitation.getInvitationTarget());
				User user = new User();
				user.setEmail(invitation.getEmail());
				user.setCompany(company);
				model.addAttribute("newUser", user);
				model.addAttribute("invitation", invitation);
				target = "user/signup";
				break;
			case USER_INVITATION:
			case INSTANCE_ADMIN_INVITATION:
			default:
				target = "stinking finger";
				break;
		}
		
		return target;
	}
}
