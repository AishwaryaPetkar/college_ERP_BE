package com.app.student.service;

import org.springframework.http.ResponseEntity;

import com.app.student.dto.StudentAddressDto;

public interface StudentAddressService {

	void saveStudentAddress(StudentAddressDto addressDTO);

}
