package com.jksoft.ezbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.Item;
import com.jksoft.ezbackend.entities.Namespace;

public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByNamespace(Namespace namespace);

	public Item findByNamespaceAndId(Namespace namespace, Long id);

}
