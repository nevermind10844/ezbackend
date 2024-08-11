package com.jksoft.ezbackend.entities;

import java.sql.Timestamp;
import java.util.EnumSet;

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

	public enum PropertyTypeEnum {
		TEXT(0L, "Text"),
		INTEGER(1L, "Ganzzahl"),
		DECIMAL(2L, "Dezimalzahl"),
		BOOLEAN(3L, "Boolscher Wert"),
		REFERENCE(4L, "Bezug"),
		SELECTION(5L, "Auswahl");

		private Long id;
		private String name;

		private PropertyTypeEnum(Long id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public static PropertyTypeEnum getEnum(String name) {
			return EnumSet.allOf(PropertyTypeEnum.class).stream()
					.filter(propertyTypeEnum -> propertyTypeEnum.getName().equals(name)).findFirst().get();
		}
	}
}
