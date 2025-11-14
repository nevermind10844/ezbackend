package com.jksoft.ezbackend.entities.structure;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "ezb_property")
public class Property {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private PropertyType propertyType;
	
	@ManyToOne
	private Item reference;
	@ManyToOne
	private Company company;
	@ManyToOne
	private Namespace namespace;
	@ManyToOne
	private Item item;

	private Boolean displayText;

	@CreationTimestamp
	private Timestamp created;
	@UpdateTimestamp
	private Timestamp updated;

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

	public Item getReference() {
		return reference;
	}

	public void setReference(Item reference) {
		this.reference = reference;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Namespace getNamespace() {
		return namespace;
	}

	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Boolean getDisplayText() {
		return displayText;
	}

	public void setDisplayText(Boolean displayText) {
		this.displayText = displayText;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Property [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", propertyType=");
		builder.append(propertyType.getName());
		builder.append(", reference=");
		builder.append(reference != null ? reference.getName() : null);
		builder.append(", company=");
		builder.append(company.getName());
		builder.append(", namespace=");
		builder.append(namespace != null ? namespace.getName() : null);
		builder.append(", item=");
		builder.append(item.getName());
		builder.append(", created=");
		builder.append(created);
		builder.append(", updated=");
		builder.append(updated);
		builder.append("]");
		return builder.toString();
	}

}
