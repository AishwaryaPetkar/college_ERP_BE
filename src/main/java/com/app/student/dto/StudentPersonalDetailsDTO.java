package com.app.student.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPersonalDetailsDTO {

    private Long studentPersonalDetailsId;

    // Basic Information
    private String maritalStatus;
    private String instituteEmail;
    private String mobile;
    private String alternateMobile;
    private String fatherName;
    private String motherName;

    // Other Information
    private String nationality;
    private String religion;
    private String caste;
    private String casteCategory;
    private String abcId;
    private String antiRaggingId;

    // Admin Information
    private String registrationNumber;
    private String universityNumber;
    private String applicationNumber;
    private String rollNumber;

    // Personal Info
    private String birthPlace;
    private String birthState;
    private String differentlyAbled;
    private String disabilityDetails;
    private String bloodGroup;
    private String identityMark;
    private String domicileCountry;
    private String domicileState;
    private String motherTongue;
    private String citizenshipCategory;
   
}

