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

@Entity(name = "ezb_namespace")
public class Namespace {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Company company;
	
	@OneToMany(mappedBy = "namespace")
	private List<Database> databaseList;
	
	@CreationTimestamp
	private Timestamp created;
	@UpdateTimestamp
	private Timestamp updated;
}
