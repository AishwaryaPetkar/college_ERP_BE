package com.app.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;

import com.app.student.dto.AcademicRecordsDto;
import com.app.student.entity.AcademicRecords;
import com.app.student.service.StudentAcademicRecordsService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/students")
public class StudentAcademicRecordsController {
	@Autowired
	private StudentAcademicRecordsService academicRecordsService;

	@PostMapping("/save-academic-records")
	public ResponseEntity<?> saveAcademicRecord(@ModelAttribute AcademicRecordsDto dto,
			@AuthenticationPrincipal(expression = "username") String username) {
		try {
			AcademicRecords saved = academicRecordsService.saveAcademicRecord(dto, username);
			return ResponseEntity.ok("Academic record saved with ID: " + saved.getId());
		} catch (MultipartException e) {
			return ResponseEntity.badRequest().body("File upload error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
		}
	}

}
