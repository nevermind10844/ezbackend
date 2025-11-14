package com.jksoft.ezbackend.service.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jksoft.ezbackend.entities.data.PropertyRecord;
import com.jksoft.ezbackend.repositories.data.RecordRepository;

@Service
public class RecordService {
	
	RecordRepository recordRepository;
	
	public RecordService(RecordRepository recordRepository) {
		this.recordRepository = recordRepository;
	}
	
	public PropertyRecord createRecord(PropertyRecord record) {
		return recordRepository.save(record);
	}
	
	public PropertyRecord readRecord(Long id) {
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		return recordRepository.getReferenceById(id);
	}
	
	public List<PropertyRecord> listRecords(){
		return recordRepository.findAll();
	}
	
	public PropertyRecord updateRecord(PropertyRecord record) {
		return recordRepository.save(record);
	}
	
	public void deleteRecord(Long id) {
		recordRepository.deleteById(id);
	}
}
