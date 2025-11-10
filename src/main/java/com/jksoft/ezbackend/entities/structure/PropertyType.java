package com.jksoft.ezbackend.entities.structure;

import java.util.stream.Stream;

public enum PropertyType {
	TEXT(0L, "Text", "txt", false, true),
	INTEGER(1L, "Ganzzahl", "int", false, false),
	DECIMAL(2L, "Dezimalzahl", "dec", false, false),
	BOOLEAN(3L, "Boolscher Wert","bit", false, false),
	REFERENCE(4L, "Bezug", "ref", true, false),
	SELECTION(5L, "Auswahl", "sel", true, false),
	EMBEDDED(6L, "Einbettung", "emb", true, false);

	private Long id;
	private String name;
	private String shortText;
	private Boolean referenceType;
	private Boolean displayTextType;

	private PropertyType(Long id, String name, String shortText, Boolean referenceType, Boolean displayTextType) {
		this.id = id;
		this.name = name;
		this.shortText = shortText;
		this.referenceType = referenceType;
		this.displayTextType = displayTextType;
	}

	public String getName() {
		return this.name;
	}
	
	public String getShortText() {
		return shortText;
	}

	public Long getId() {
		return this.id;
	}

	public Boolean getReferenceType() {
		return referenceType;
	}

	public Boolean getDisplayTextType() {
		return displayTextType;
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
	
	public static PropertyType getEnumFromShortText(String shortText) {
		return Stream.of(PropertyType.values())
				.filter(propertyType -> propertyType.getShortText().equals(shortText))
				.findFirst().orElseThrow();
	}
}
