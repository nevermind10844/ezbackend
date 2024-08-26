package com.jksoft.ezbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Property;
import com.jksoft.ezbackend.entities.Namespace;
import com.jksoft.ezbackend.repositories.PropertyRepository;

@Service
public class PropertyService {
	
	@Autowired
	PropertyRepository propertyRepository;
	
	public Property createProperty(Property property) {
		return propertyRepository.save(property);
	}
	
	public Property readProperty(Long id) {
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		return propertyRepository.getReferenceById(id);
	}
	
	public Property readProperty(Company company, Long id) {
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		if(company == null)
			throw new IllegalArgumentException("company must not be null");
		return propertyRepository.findByCompanyAndId(company, id);
	}
	
	
	public List<Property> listPropertys(){
		return propertyRepository.findAll();
	}
	
	public Property updateProperty(Property property) {
		return propertyRepository.save(property);
	}
	
	public void deleteProperty(Long id) {
		propertyRepository.deleteById(id);
	}
}
