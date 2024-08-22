package com.jksoft.ezbackend.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "ezb_property")
public class Property {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private PropertyType propertyType;

	@ManyToOne
	private Company company;
	@ManyToOne
	private Namespace namespace;
	@ManyToOne
	private Item item;
	
	@CreationTimestamp
	private Timestamp created;
	@UpdateTimestamp
	private Timestamp updated;
}
