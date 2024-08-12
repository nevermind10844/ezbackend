package com.jksoft.ezbackend.entities.converter;

import com.jksoft.ezbackend.entities.PropertyType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PropertyTypeConverter implements AttributeConverter<PropertyType, Long> {

	@Override
	public Long convertToDatabaseColumn(PropertyType attribute) {
		return attribute.getId();
	}

	@Override
	public PropertyType convertToEntityAttribute(Long dbData) {
		return PropertyType.getEnum(dbData);
	}

}