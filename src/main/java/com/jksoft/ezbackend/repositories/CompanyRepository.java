package com.jksoft.ezbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
