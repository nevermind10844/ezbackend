package com.jksoft.ezbackend.repositories.structure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.structure.Company;
import com.jksoft.ezbackend.entities.structure.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
	public Property findByCompanyAndId(Company company, Long id);
}
