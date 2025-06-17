package com.app.student.entity;

import javax.persistence.*;

import com.app.auth.entities.User;
import lombok.*;

@Entity
@Table(name = "student_personal_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentPersonalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // Association with User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
