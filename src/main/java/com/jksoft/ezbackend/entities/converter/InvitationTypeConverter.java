package com.jksoft.ezbackend.entities.converter;

import com.jksoft.ezbackend.entities.Invitation.InvitationType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InvitationTypeConverter implements AttributeConverter<InvitationType, Long> {

	@Override
	public Long convertToDatabaseColumn(InvitationType attribute) {
		return attribute.getId();
	}

	@Override
	public InvitationType convertToEntityAttribute(Long dbData) {
		return InvitationType.getEnum(dbData);
	}

}