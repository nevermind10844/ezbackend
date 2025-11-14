package com.jksoft.ezbackend.entities.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.jksoft.ezbackend.entities.data.ItemWrapper;
import com.jksoft.ezbackend.entities.data.PropertyRecord;
import com.jksoft.ezbackend.entities.structure.dto.ItemDTO;
import com.jksoft.ezbackend.entities.structure.dto.PropertyDTO;

public class WrapperDTO {
	private Long id;
	private ItemDTO item;
	private List<RecordDTO> recordList;
	
	public WrapperDTO() {
		this.recordList = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}

	public List<RecordDTO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<RecordDTO> recordList) {
		this.recordList = recordList;
	}
	
	public void addRecord(RecordDTO recordDto) {
		this.recordList.add(recordDto);
	}
	
	public String getDisplayName() {
		String s = new String();
		s += this.getId();
		for (RecordDTO r : recordList) {
			PropertyDTO p = r.getProperty();
			if(p.getDisplayText() != null && r.getProperty().getDisplayText().equals(true)) {
				if(s.length() > 0)
					s += " - ";
				s += r.getValue();
			}
		}
		
		return s;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WrapperDTO [id=");
		builder.append(id);
		builder.append(", item=");
		builder.append(item);
		builder.append(", recordList=");
		builder.append(recordList);
		builder.append("]");
		return builder.toString();
	}
	
	public static WrapperDTO fromItemWrapper(ItemWrapper itemWrapper) {
		if(itemWrapper == null)
			return null;
		WrapperDTO wrapper = new WrapperDTO();
		wrapper.setId(itemWrapper.getId());
		wrapper.setItem(ItemDTO.fromItem(itemWrapper.getItem()));
		for (PropertyRecord propertyRecord : itemWrapper.getRecordList()) {
			 wrapper.addRecord(RecordDTO.fromPropertyRecord(propertyRecord));
		}
		return wrapper;
	}
	
	public ItemWrapper toItemWrapper() {
		ItemWrapper itemWrapper = new ItemWrapper();
		itemWrapper.setId(this.id);
		for (RecordDTO recordDTO : recordList) {
			PropertyRecord propertyRecord = recordDTO.toPropertyRecord();
			propertyRecord.setItemWrapper(itemWrapper);
			itemWrapper.addPropertyRecord(propertyRecord);
		}
		itemWrapper.setItem(this.item.toItem());
		return itemWrapper;
	}
}
