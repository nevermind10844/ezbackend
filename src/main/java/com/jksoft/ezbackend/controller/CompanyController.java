package com.jksoft.ezbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.service.CompanyService;

@Controller
public class CompanyController {
	@Autowired
	CompanyService companyService;

	@GetMapping("/instance/company")
	public String getCompanyList(Model model) {
		model.addAttribute("companyList", this.companyService.listCompanies());
		model.addAttribute("newCompany", new Company());

		return "company/instance/companyList";
	}

	@GetMapping("/instance/company/{companyId}")
	public String getCompany(Model model, @PathVariable Long companyId) {
		model.addAttribute("company", this.companyService.readCompany(companyId));

		return "company/instance/companyDetails";
	}

	@PostMapping("/instance/company")
	public String createCompany(Model model, Company company) {
		companyService.createCompany(company);

		return "redirect:/instance/company";
	}
}
