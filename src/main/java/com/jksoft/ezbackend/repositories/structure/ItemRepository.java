package com.jksoft.ezbackend.repositories.structure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.structure.Company;
import com.jksoft.ezbackend.entities.structure.Item;
import com.jksoft.ezbackend.entities.structure.Namespace;

public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByNamespace(Namespace namespace);

	public Item findByCompanyAndId(Company company, Long id);

}
