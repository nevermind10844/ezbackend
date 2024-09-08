package com.jksoft.ezbackend.entities;

import java.util.stream.Stream;

public enum PropertyType {
	TEXT(0L, "Text", false),
	INTEGER(1L, "Ganzzahl", false),
	DECIMAL(2L, "Dezimalzahl", false),
	BOOLEAN(3L, "Boolscher Wert", false),
	REFERENCE(4L, "Bezug", true),
	SELECTION(5L, "Auswahl", true),
	EMBEDDED(6L, "Einbettung", true);

	private Long id;
	private String name;
	private Boolean referenceType;

	private PropertyType(Long id, String name, Boolean referenceType) {
		this.id = id;
		this.name = name;
		this.referenceType = referenceType;
	}

	public String getName() {
		return this.name;
	}
	
	public Long getId() {
		return this.id;
	}

	public Boolean getReferenceType() {
		return referenceType;
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
