package com.jksoft.ezbackend.entities.data;

import java.util.ArrayList;
import java.util.List;

import com.jksoft.ezbackend.entities.structure.Property;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity(name = "ezb_record")
public class PropertyRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private ItemWrapper itemWrapper;
	
	@ManyToOne
	private Property property;
	
	private String value;
	
	@ManyToOne
	private ItemWrapper reference;
	
	@Transient
	private List<ItemWrapper> referenceList;

	public PropertyRecord() {
		this.referenceList = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemWrapper getItemWrapper() {
		return itemWrapper;
	}

	public void setItemWrapper(ItemWrapper itemWrapper) {
		this.itemWrapper = itemWrapper;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ItemWrapper getReference() {
		return reference;
	}

	public void setReference(ItemWrapper reference) {
		this.reference = reference;
	}

	public List<ItemWrapper> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<ItemWrapper> referenceList) {
		this.referenceList = referenceList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyRecord [id=");
		builder.append(id);
		builder.append(", itemWrapper=");
		builder.append(itemWrapper);
		builder.append(", property=");
		builder.append(property);
		builder.append(", value=");
		builder.append(value);
		builder.append(", referenceList=");
		builder.append(referenceList);
		builder.append("]");
		return builder.toString();
	}

	
	
}

