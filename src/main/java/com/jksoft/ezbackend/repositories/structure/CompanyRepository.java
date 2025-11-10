package com.jksoft.ezbackend.repositories.structure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.structure.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
