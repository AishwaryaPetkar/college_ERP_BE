package com.app.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.student.dto.AdmissionInformationDTO;
import com.app.student.service.AdmissionInformationService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/students")
public class StudentAdmissionController {
	@Autowired
	private AdmissionInformationService admissionInformationService;
	
	@PostMapping("/save-admission-information")
	private ResponseEntity<?> saveAdmissionInformation(@RequestBody AdmissionInformationDTO dto) {
		return admissionInformationService.saveAdmissionDetails(dto);
	}
}
