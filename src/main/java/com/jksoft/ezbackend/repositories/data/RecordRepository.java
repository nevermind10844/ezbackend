package com.jksoft.ezbackend.repositories.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.data.PropertyRecord;

public interface RecordRepository extends JpaRepository<PropertyRecord, Long>{

}
