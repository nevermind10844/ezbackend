package com.jksoft.ezbackend.entities.structure.dto;

import com.jksoft.ezbackend.entities.structure.Property;
import com.jksoft.ezbackend.entities.structure.PropertyType;

public class PropertyDTO {
	private Long id;
	private String name;
	private PropertyType propertyType;
	private Boolean displayText;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public Boolean getDisplayText() {
		return displayText;
	}

	public void setDisplayText(Boolean displayText) {
		this.displayText = displayText;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyDTO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", propertyType=");
		builder.append(propertyType);
		builder.append(", displayText=");
		builder.append(displayText);
		builder.append("]");
		return builder.toString();
	}
	
	public Property toProperty() {
		Property property = new Property();
		property.setId(this.id);
		property.setName(this.name);
		property.setDisplayText(this.displayText);
		property.setPropertyType(this.getPropertyType());
		return property;
	}
	
	public static PropertyDTO fromProperty(Property property) {
		PropertyDTO propertyDTO = new PropertyDTO();
		propertyDTO.setId(property.getId());
		propertyDTO.setName(property.getName());
		propertyDTO.setDisplayText(property.getDisplayText());
		propertyDTO.setPropertyType(property.getPropertyType());
		return propertyDTO;
	}
}
