package com.jksoft.ezbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jksoft.ezbackend.entities.Company;
import com.jksoft.ezbackend.entities.Item;
import com.jksoft.ezbackend.entities.Namespace;
import com.jksoft.ezbackend.repositories.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	public Item createItem(Item item) {
		return itemRepository.save(item);
	}
	
	public Item readItem(Long id) {
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		return itemRepository.getReferenceById(id);
	}
	
	public Item readItem(Company company, Long id) {
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		if(company == null)
			throw new IllegalArgumentException("company must not be null");
		return itemRepository.findByCompanyAndId(company, id);
	}
	
	
	public List<Item> listItems(){
		return itemRepository.findAll();
	}
	
	public List<Item> listItems(Namespace namespace){
		return itemRepository.findByNamespace(namespace);
	}
	
	public Item updateItem(Item item) {
		return itemRepository.save(item);
	}
	
	public void deleteItem(Long id) {
		itemRepository.deleteById(id);
	}
}
