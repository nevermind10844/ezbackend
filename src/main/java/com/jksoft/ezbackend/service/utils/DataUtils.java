package com.jksoft.ezbackend.service.utils;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.jksoft.ezbackend.entities.data.dto.RecordDTO;
import com.jksoft.ezbackend.entities.data.dto.WrapperDTO;
import com.jksoft.ezbackend.entities.structure.dto.PropertyDTO;
import com.jksoft.ezbackend.service.PropertyService;
import com.jksoft.ezbackend.service.data.WrapperService;

@Service
public class DataUtils {
	private PropertyService propertyService;
	private WrapperService wrapperService;
	
	public DataUtils(PropertyService propertyService, WrapperService wrapperService) {
		this.propertyService = propertyService;
		this.wrapperService = wrapperService;
	}
	
	public RecordDTO getRecordFromFormData(String name, String value) {
		String[] splitString = name.split("-");
		String[] splitIds = splitString[2].split("_");
		
		Long propertyId = Long.parseLong(splitIds[2]);
		
		PropertyDTO property = PropertyDTO.fromProperty(this.propertyService.readProperty(propertyId));

		RecordDTO r = new RecordDTO();
		r.setProperty(property);
		
		switch(r.getProperty().getPropertyType()) {
			case BOOLEAN:
				break;
			case DECIMAL:
				break;
			case INTEGER:
				break;
			case REFERENCE:
			case EMBEDDED:
				WrapperDTO referencedWrapper = wrapperService.readWrapper(Long.parseLong(value));
				r.setReference(referencedWrapper);
				break;
			case SELECTION:
				break;
			case TEXT:
				r.setValue(value);
				break;
			default:
				break;
			
		}
		
		return r;
	}
	
	
	/**
	 * values here will be in the form of prop-txt_{namespaceId}_{itemId}_{propertyId}
	 * the "-txt" / "-ref" / ... part is just for clarity and easier debugging
	 * the namespaceId is for clarity and potential later use
	 * the itemId is for clarity and potential later use
	 * the propertyId is for identifying the correct property
	 * 
	 * @param values
	 * @return
	 */
	public WrapperDTO getWrapperFromFormData(Map<String, String> values) {
		WrapperDTO wrapper = new WrapperDTO();
		
		for (Map.Entry<String, String> entry: values.entrySet()) {
			if(entry.getKey().startsWith("prop-")) {
				RecordDTO r = getRecordFromFormData(entry.getKey(), entry.getValue());
				wrapper.addRecord(r);
			}
		}
		
		return wrapper;
	}
}
