package com.app.student.service;

import org.springframework.http.ResponseEntity;

import com.app.student.dto.StudentPersonalDetailsDTO;

public interface StudentBasicInformationService {

	ResponseEntity<?> saveBasicDetails(StudentPersonalDetailsDTO dto);
}
