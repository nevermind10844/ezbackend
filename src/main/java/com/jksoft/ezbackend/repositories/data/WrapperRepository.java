package com.jksoft.ezbackend.repositories.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jksoft.ezbackend.entities.data.ItemWrapper;
import com.jksoft.ezbackend.entities.structure.Item;

@Repository
public interface WrapperRepository extends JpaRepository<ItemWrapper, Long> {
	
	public List<ItemWrapper> findByItem(Item item);
	public List<ItemWrapper> findByItem_Id(Long itemId);
}
