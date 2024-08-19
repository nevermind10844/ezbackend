package com.jksoft.ezbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Namespace;

public interface NamespaceRepository extends JpaRepository<Namespace, Long> {
	public List<Namespace> findByCompany(Company company);

	public Namespace findByCompanyAndId(Company company, Long id);

}
