package com.app.student.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.auth.entities.User;
import com.app.auth.repository.UserRepository;
import com.app.student.dto.AcademicRecordsDto;
import com.app.student.entity.AcademicRecords;
import com.app.student.repository.StudentAcademicRecordsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentAcademicRecordServiceImpl implements StudentAcademicRecordsService {
	@Autowired
	private StudentAcademicRecordsRepository academicRecordsRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;	

	@Value("${file.upload-dir}") // define this in application.properties/yml
	private String uploadDir;

	public AcademicRecords saveAcademicRecord(AcademicRecordsDto dto, String username) throws IOException {
		// Find user
		User user = userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
		
		// Map DTO to Entity
		AcademicRecords record = modelMapper.map(dto, AcademicRecords.class);

		// Handle file uploads
		if (dto.getXeroxOfDegreeMarksheet() != null && !dto.getXeroxOfDegreeMarksheet().isEmpty()) {
			String xeroxPath = saveFile(dto.getXeroxOfDegreeMarksheet(), username);
			record.setXeroxOfDegreeMarksheet(xeroxPath);
		}

		if (dto.getDegreeMarksheet() != null && !dto.getDegreeMarksheet().isEmpty()) {
			String degreePath = saveFile(dto.getDegreeMarksheet());
			record.setDegreeMarksheet(degreePath);
		}

		// Set user
		record.setUser(user);

		// Save to DB
		return academicRecordsRepository.save(record);
	}

	private String saveFile(MultipartFile file, String username) throws IOException {
	    // Convert to absolute path if needed
	    File uploadPath = new File(System.getProperty("user.dir"), uploadDir+"/"+username);
	    
	    if (!uploadPath.exists()) {
	        boolean created = uploadPath.mkdirs();
	        if (!created) {
	            throw new IOException("Failed to create directory: " + uploadPath.getAbsolutePath());
	        }
	    }

	    String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
	    File dest = new File(uploadPath, filename);
	    file.transferTo(dest);

	    return filename;
	}


}
