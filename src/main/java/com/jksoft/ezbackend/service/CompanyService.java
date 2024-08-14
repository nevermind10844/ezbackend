package com.jksoft.ezbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.repositories.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepository companyRepository;
	
	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}
	
	public Company readCompany(Long id) {
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		return companyRepository.getReferenceById(id);
	}
	
	public List<Company> listCompanies(){
		return companyRepository.findAll();
	}
	
	public Company updateCompany(Company company) {
		return companyRepository.save(company);
	}
	
	public void deleteCompany(Long id) {
		companyRepository.deleteById(id);
	}
}
