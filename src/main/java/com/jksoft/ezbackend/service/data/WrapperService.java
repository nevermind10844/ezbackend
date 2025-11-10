package com.jksoft.ezbackend.service.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jksoft.ezbackend.entities.data.ItemWrapper;
import com.jksoft.ezbackend.entities.data.PropertyRecord;
import com.jksoft.ezbackend.entities.data.dto.WrapperDTO;
import com.jksoft.ezbackend.entities.structure.Item;
import com.jksoft.ezbackend.repositories.data.WrapperRepository;

@Service
public class WrapperService {

	WrapperRepository wrapperRepository;
	RecordService recordService;

	public WrapperService(WrapperRepository wrapperRepository, RecordService recordService) {
		this.wrapperRepository = wrapperRepository;
		this.recordService = recordService;
	}

	public ItemWrapper createWrapper(ItemWrapper wrapper) {
		ItemWrapper persistedWrapper = wrapperRepository.save(wrapper);
		
		for (PropertyRecord record : persistedWrapper.getRecordList()) {
			this.recordService.createRecord(record);
		}
		
		return persistedWrapper;
	}

	public WrapperDTO readWrapper(Long id) {
		if (id == null)
			throw new IllegalArgumentException("id must not be null");
		ItemWrapper wrapper = wrapperRepository.getReferenceById(id);
		
		for(PropertyRecord r : wrapper.getRecordList()) {
			switch (r.getProperty().getPropertyType()) {
				case BOOLEAN:
					break;
				case DECIMAL:
					break;
				case INTEGER:
					break;
				case EMBEDDED:
				case REFERENCE:
					Item referencedItem = r.getProperty().getReference();
					List<ItemWrapper> referencedWrapperList = this.queryWrappers(referencedItem.getId());
					r.setReferenceList(referencedWrapperList);
					break;
				case SELECTION:
					break;
				case TEXT:
				default:
					break;
			}
		}
		
		WrapperDTO wrapperDto = WrapperDTO.fromItemWrapper(wrapper);
		return wrapperDto;
	}

	public List<ItemWrapper> listWrappers() {
		return wrapperRepository.findAll();
	}

	public ItemWrapper updateWrapper(ItemWrapper wrapper) {
		return wrapperRepository.save(wrapper);
	}

	public void deleteWrapper(Long id) {
		wrapperRepository.deleteById(id);
	}

	public List<ItemWrapper> queryWrappers(Item item) {
		return this.wrapperRepository.findByItem(item);
	}
	
	public List<ItemWrapper> queryWrappers(Long itemId){
		return this.wrapperRepository.findByItem_Id(itemId);
	}

}
