package com.jksoft.ezbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Namespace;
import com.jksoft.ezbackend.repositories.NamespaceRepository;

@Service
public class NamespaceService {
	
	@Autowired
	NamespaceRepository namespaceRepository;
	
	public Namespace createNamespace(Namespace namespace) {
		return namespaceRepository.save(namespace);
	}
	
	public Namespace readNamespace(Long id) {
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		return namespaceRepository.getReferenceById(id);
	}
	
	public Namespace readNamespace(Company company, Long id) {
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		if(company == null)
			throw new IllegalArgumentException("company must not be null");
		return namespaceRepository.findByCompanyAndId(company, id);
	}
	
	
	public List<Namespace> listNamespaces(){
		return namespaceRepository.findAll();
	}
	
	public List<Namespace> listNamespaces(Company company){
		return namespaceRepository.findByCompany(company);
	}
	
	public Namespace updateNamespace(Namespace namespace) {
		return namespaceRepository.save(namespace);
	}
	
	public void deleteNamespace(Long id) {
		namespaceRepository.deleteById(id);
	}
}
