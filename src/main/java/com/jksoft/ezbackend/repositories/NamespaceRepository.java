package com.jksoft.ezbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.Namespace;

public interface NamespaceRepository extends JpaRepository<Namespace, Long> {

}
