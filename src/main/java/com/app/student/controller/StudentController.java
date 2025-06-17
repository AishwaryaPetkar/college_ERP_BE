package com.app.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.student.dto.StudentPersonalDetailsDTO;
import com.app.student.service.StudentBasicInformationService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/students")
public class StudentController {
	@Autowired
	private StudentBasicInformationService studentBasicInformationService;
	
	@PostMapping("/save-basic-information")
	private ResponseEntity<?> saveStudentBasicDetails(@RequestBody StudentPersonalDetailsDTO dto){
		return studentBasicInformationService.saveBasicDetails(dto);
		
	}

}
