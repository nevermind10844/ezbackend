package com.jksoft.ezbackend.repositories.structure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.structure.Company;
import com.jksoft.ezbackend.entities.structure.Namespace;

public interface NamespaceRepository extends JpaRepository<Namespace, Long> {
	public List<Namespace> findByCompany(Company company);

	public Namespace findByCompanyAndId(Company company, Long id);

}
