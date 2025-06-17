package com.app.student.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@Entity
//@Table(name = "student_academic_status")
public class StudentAcademicStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currentSession;
    private String currentYear;
    private String currentSemester;
    private String classSection;
    private String admissionStatus;

    @OneToOne
    @JoinColumn(name = "student_id")
    private StudentPersonalDetails studentPersonalDetailsId;
}

