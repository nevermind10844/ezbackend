package com.jksoft.ezbackend.entities;

import java.util.stream.Stream;

public enum PropertyType {
	TEXT(0L, "Text"),
	INTEGER(1L, "Ganzzahl"),
	DECIMAL(2L, "Dezimalzahl"),
	BOOLEAN(3L, "Boolscher Wert"),
	REFERENCE(4L, "Bezug"),
	SELECTION(5L, "Auswahl");

	private Long id;
	private String name;

	private PropertyType(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public Long getId() {
		return this.id;
	}

	public static PropertyType getEnum(String name) {
		return Stream.of(PropertyType.values())
				.filter(propertyType -> propertyType.getName().equals(name))
				.findFirst().orElseThrow();
	}
	
	public static PropertyType getEnum(Long id) {
		return Stream.of(PropertyType.values())
				.filter(propertyType -> propertyType.getId().equals(id))
				.findFirst().orElseThrow();
	}
}
