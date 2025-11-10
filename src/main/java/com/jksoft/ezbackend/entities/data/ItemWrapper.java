package com.jksoft.ezbackend.entities.data;

import java.util.ArrayList;
import java.util.List;

import com.jksoft.ezbackend.entities.structure.Item;
import com.jksoft.ezbackend.entities.structure.Property;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "ezb_wrapper")
public class ItemWrapper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Item item;
	@OneToMany(mappedBy = "itemWrapper")
	private List<PropertyRecord> recordList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<PropertyRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<PropertyRecord> recordList) {
		this.recordList = recordList;
	}
	
	public void addPropertyRecord(PropertyRecord propertyRecord) {
		if(this.recordList == null)
			this.recordList = new ArrayList<>();
		this.recordList.add(propertyRecord);
	}

	public String getDisplayName() {
		String s = new String();
		s += this.getId();
		for (PropertyRecord propertyRecord : recordList) {
			Property p = propertyRecord.getProperty();
			if(p.getDisplayText() != null && propertyRecord.getProperty().getDisplayText().equals(true)) {
				if(s.length() > 0)
					s += " - ";
				s += propertyRecord.getValue();
			}
		}
		
		return s;
	}



}