package com.app.student.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicRecordsDto {

    // Educational Qualification Fields
	@NotBlank
    private String examPassed;
	@NotBlank
    private String boardOrUniversity;
	@NotBlank
    private String instituteOrSchool;
	@NotBlank
    private String rollNumber;
	@NotBlank
    private String passingYear;
	@NotBlank
    private String resultType;
	@NotBlank
    private String percentage;
	@NotBlank
    private String stream;
	@NotBlank
    private String medium;
	@NotBlank
    private String mode;
	@NotBlank
    private String gapReason;
	@NotBlank
    private String gapYear;
	@NotBlank
    private String resultStatus;
	@NotBlank
    private String examRank;
	@NotBlank
    private String degreeName;
	@NotBlank
    private String branchName;
	@NotBlank
    private String durationInYear;
	@NotBlank
    private String discipline;
	@NotBlank
    private String examScheme;
	@NotBlank
    private String enrollmentNumber;
	@NotBlank
    private String markObtained;
	@NotBlank
    private String outOfMarked;

    // File Uploads
    private MultipartFile xeroxOfDegreeMarksheet;
    private MultipartFile degreeMarksheet;
}

