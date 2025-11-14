package com.jksoft.ezbackend.entities.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.jksoft.ezbackend.entities.data.ItemWrapper;
import com.jksoft.ezbackend.entities.data.PropertyRecord;
import com.jksoft.ezbackend.entities.structure.dto.PropertyDTO;

public class RecordDTO {
	private Long id;
	private PropertyDTO property;
	private String value;
	private WrapperDTO reference;
	private List<WrapperDTO> referenceList;
	
	public RecordDTO() {
		this.referenceList = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PropertyDTO getProperty() {
		return property;
	}

	public void setProperty(PropertyDTO property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public WrapperDTO getReference() {
		return reference;
	}

	public void setReference(WrapperDTO reference) {
		this.reference = reference;
	}
	
	public List<WrapperDTO> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<WrapperDTO> referenceList) {
		this.referenceList = referenceList;
	}
	
	public void addReference(WrapperDTO wrapperDTO) {
		this.referenceList.add(wrapperDTO);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecordDTO [id=");
		builder.append(id);
		builder.append(", property=");
		builder.append(property);
		builder.append(", value=");
		builder.append(value);
		builder.append(", reference=");
		builder.append(reference == null ? null : reference.getDisplayName());
		builder.append(", referenceList=");
		builder.append(referenceList);
		builder.append("]");
		return builder.toString();
	}
	
	public String getPrettyPrint() {
		if(this.property.getPropertyType().getReferenceType()) {
			return "Property '%s' having value '%s'".formatted(this.getProperty().getName(), this.reference.getDisplayName());
		} else {
			return "Property '%s' having value '%s'".formatted(this.getProperty().getName(), this.value);
		}
	}
	
	public void prettyPrint() {
		System.out.println(this.getPrettyPrint());
	}

	public static RecordDTO fromPropertyRecord(PropertyRecord propertyRecord) {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setId(propertyRecord.getId());
		recordDTO.setProperty(PropertyDTO.fromProperty(propertyRecord.getProperty()));
		for (ItemWrapper itemWrapper : propertyRecord.getReferenceList()) {
			recordDTO.addReference(WrapperDTO.fromItemWrapper(itemWrapper));
		}
		recordDTO.setReference(WrapperDTO.fromItemWrapper(propertyRecord.getReference()));
		recordDTO.setValue(propertyRecord.getValue());
		return recordDTO;
	}
	
	public PropertyRecord toPropertyRecord() {
		PropertyRecord propertyRecord = new PropertyRecord();
		propertyRecord.setId(this.id);
		propertyRecord.setValue(this.value);
		propertyRecord.setProperty(this.property.toProperty());
		propertyRecord.setReference(this.reference != null ? this.reference.toItemWrapper() : null);
		return propertyRecord;
	}
}
