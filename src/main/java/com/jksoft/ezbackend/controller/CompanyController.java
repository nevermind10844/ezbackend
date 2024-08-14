package com.jksoft.ezbackend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Invitation;
import com.jksoft.ezbackend.entities.Invitation.InvitationType;
import com.jksoft.ezbackend.service.CompanyService;
import com.jksoft.ezbackend.service.InvitationService;

@Controller
public class CompanyController {
	@Autowired
	CompanyService companyService;
	
	@Autowired
	InvitationService invitationService;

	@GetMapping("/instance/company")
	public String getCompanyList(Model model) {
		model.addAttribute("companyList", this.companyService.listCompanies());
		model.addAttribute("newCompany", new Company());

		return "company/instance/companyList";
	}

	@GetMapping("/instance/company/{companyId}")
	public String getCompany(Model model, @PathVariable Long companyId) {
		model.addAttribute("company", this.companyService.readCompany(companyId));
		
		Invitation invitation = new Invitation();
		invitation.setInvitationTarget(companyId);
		invitation.setInvitationType(InvitationType.ADMIN_INVITATION);
		invitation.setInvitationKey(UUID.randomUUID().toString());
		model.addAttribute("newInvitation", invitation);
		
		List<Invitation> invitationList = this.invitationService.listCompanyInvitations(companyId);
		model.addAttribute("invitationList", invitationList);
		
		return "company/instance/companyDetails";
	}

	@PostMapping("/instance/company")
	public String createCompany(Model model, Company company) {
		companyService.createCompany(company);

		return "redirect:/instance/company";
	}
	
	@PostMapping("/instance/company/{companyId}/invitation")
	public String createCompanyInvitation(Model model, @PathVariable Long companyId, Invitation invitation) {
		this.invitationService.createInvitation(invitation);
		
		return String.format("redirect:/instance/company/%d", companyId);
	}
}
