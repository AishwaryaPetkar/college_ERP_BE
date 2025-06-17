package com.app.student.entity;

import java.time.LocalDate;
import javax.persistence.*;

import com.app.auth.entities.User;
import lombok.*;

@Entity
@Table(name = "admission_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdmissionInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // Association with User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
