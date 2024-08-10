package com.jksoft.ezbackend.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "ezb_propertyType")
public class PropertyType {
	@Id
	private Long id;
	
	private String name;
	
	@CreationTimestamp
	private Timestamp created;
	@UpdateTimestamp
	private Timestamp updated;
	
	public enum PropertyTypeEnum{
		TEXT("Text"),
		INTEGER("Ganzzahl"),
		DECIMAL("Dezimalzahl"),
		BOOLEAN("Boolscher Wert"),
		REFERENCE("Bezug"),
		SELECTION("Auswahl");
		
		private String name;
		
		private PropertyTypeEnum(String name) {
			this.name = name;
		}
	}
}
