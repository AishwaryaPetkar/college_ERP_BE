package com.app.student.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.app.student.dto.AcademicRecordsDto;
import com.app.student.dto.StudentPersonalDetailsDTO;
import com.app.student.entity.AcademicRecords;

public interface StudentAcademicRecordsService {

	AcademicRecords saveAcademicRecord(AcademicRecordsDto dto, String username) throws IOException;

}
