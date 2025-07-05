package com.app.student.dto;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionInformationDTO {

    private Long id;

    // Enrollment Information
    private LocalDate dateOfAdmission;
    private String enrollmentSession;
    private String academicBatch;
    private String degree;
    private String branch;
    private String scheme;
    private String admissionCategory;
    private String feesCategory;
    private String studentGroup;
    private String year;
    private String semester;
    private String enrollmentType;
    private String shift;
    private String entranceRegNo;
    private String entranceMeritNo;
    private String referenceName;

    // Current Academic Info
    private String currentSession;
    private String currentYear;
    private String currentSemester;
    private String classSection;
    private String admissionStatus;
    private String currentDegree;
    private String currentBranch;
    private String currentScheme;

    // User Identifier (not the full User entity)
//    private Long userId;
}
