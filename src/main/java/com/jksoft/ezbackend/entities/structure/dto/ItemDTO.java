package com.jksoft.ezbackend.entities.structure.dto;

import java.util.ArrayList;
import java.util.List;

import com.jksoft.ezbackend.entities.structure.Item;
import com.jksoft.ezbackend.entities.structure.Property;

public class ItemDTO {
	private Long id;
	private String name;
	private String namespace;
	private Long namespaceId;
	private List<PropertyDTO> propertyList;

	public ItemDTO() {
		this.propertyList = new ArrayList<>();
	}

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

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public Long getNamespaceId() {
		return namespaceId;
	}

	public void setNamespaceId(Long namespaceId) {
		this.namespaceId = namespaceId;
	}

	public List<PropertyDTO> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<PropertyDTO> propertyList) {
		this.propertyList = propertyList;
	}

	public void addProperty(PropertyDTO property) {
		this.propertyList.add(property);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemDTO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", namespace=");
		builder.append(namespace);
		builder.append(", namespaceId=");
		builder.append(namespaceId);
		builder.append(", propertyList=");
		builder.append(propertyList);
		builder.append("]");
		return builder.toString();
	}
	
	public Item toItem() {
		Item item = new Item();
		item.setId(this.id);
		item.setName(this.getName());
		for (PropertyDTO propertyDTO : propertyList) {
			item.addProperty(propertyDTO.toProperty());
		}
		return item;
	}

	public static ItemDTO fromItem(Item item) {
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(item.getId());
		itemDTO.setName(item.getName());
		itemDTO.setNamespace(item.getNamespace().getName());
		itemDTO.setNamespaceId(item.getNamespace().getId());
		for (Property property : item.getPropertyList()) {
			itemDTO.addProperty(PropertyDTO.fromProperty(property));
		}
		return itemDTO;
	}

}
