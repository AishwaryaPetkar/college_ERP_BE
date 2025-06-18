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
import com.app.student.dto.StudentPersonalDetailsDTO;
import com.app.student.entity.StudentPersonalDetails;
import com.app.student.repository.StudentRepository;

@Service
public class StudentBasicInformationServiceImpl implements StudentBasicInformationService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<?> saveBasicDetails(StudentPersonalDetailsDTO dto) {
		try {
			StudentPersonalDetails personalDetails = modelMapper.map(dto, StudentPersonalDetails.class);
			String email = SecurityContextHolder.getContext().getAuthentication().getName();

			User user = userRepository.findByUserName(email)					
			    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
			personalDetails.setUser(user);
			studentRepository.save(personalDetails);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(Map.of("successStatus", true, "message", "Data saved successfully!"));

		} catch (Exception e) {
			// Log the error for debugging (optional)
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("successStatus", false, "message", "Failed to save data: " + e.getMessage()));
		}
	}

}
