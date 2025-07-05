package com.app.student.service;

import org.springframework.http.ResponseEntity;

import com.app.student.dto.AdmissionInformationDTO;

public interface AdmissionInformationService {

	ResponseEntity<?> saveAdmissionDetails(AdmissionInformationDTO dto);

}
