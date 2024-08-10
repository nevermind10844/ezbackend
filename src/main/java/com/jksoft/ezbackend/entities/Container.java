package com.jksoft.ezbackend.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "ezb_container")
public class Container {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;

	@ManyToOne
	private Namespace namespace;

	@OneToMany(mappedBy = "container")
	private List<Item> itemList;

	@CreationTimestamp
	private Timestamp created;
	@UpdateTimestamp
	private Timestamp updated;
}
