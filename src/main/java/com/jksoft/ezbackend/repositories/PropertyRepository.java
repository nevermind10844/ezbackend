package com.jksoft.ezbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
	public Property findByCompanyAndId(Company company, Long id);
}
