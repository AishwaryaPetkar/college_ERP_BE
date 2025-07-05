package com.app.student.service;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.auth.entities.User;
import com.app.auth.repository.UserRepository;
import com.app.student.dto.AdmissionInformationDTO;
import com.app.student.entity.AdmissionInformation;
import com.app.student.repository.StudentAdmissionRepository;

@Service
public class AdmissionInformationServiceImpl implements AdmissionInformationService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private StudentAdmissionRepository studentAdmissionRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<?> saveAdmissionDetails(AdmissionInformationDTO dto) {
		try {
			AdmissionInformation admissionDetails = modelMapper.map(dto, AdmissionInformation.class);
			String email = SecurityContextHolder.getContext().getAuthentication().getName();

			User user = userRepository.findByUserName(email)
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			admissionDetails.setUser(user);
			studentAdmissionRepository.save(admissionDetails);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(Map.of("successStatus", true, "message", "Data saved successfully!"));
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("successStatus", false, "message", "Failed to save data: " + e.getMessage()));
		}
	}

}
