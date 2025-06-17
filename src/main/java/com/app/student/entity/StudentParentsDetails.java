package com.app.student.entity;

import javax.persistence.*;

import com.app.auth.entities.User;
import lombok.*;

@Entity
@Table(name = "student_parents_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentParentsDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Father's Information
    private String fatherTitle;
    private String fatherFirstName;
    private String fatherLastName;
    private String fatherEmail;
    private String fatherMobileNumber;
    private String fatherDesignation;
    private String fatherOccupation;
    private String fatherOrganizationName;
    private String fatherAnnualIncome;

    // Mother's Information
    private String motherTitle;
    private String motherFirstName;
    private String motherLastName;
    private String motherEmail;
    private String motherMobileNumber;
    private String motherDesignation;
    private String motherOccupation;
    private String motherOrganizationName;
    private String motherAnnualIncome;

    // Local Guardian Information
    private String guardianTitle;
    private String guardianFirstName;
    private String guardianLastName;
    private String guardianEmail;
    private String guardianMobileNumber;
    private String guardianDesignation;
    private String guardianOccupation;
    private String guardianOrganizationName;
    private String guardianAnnualIncome;

    // Additional Info
    private String familyIncome;
    private String relationshipWithStudent;

    // Association with User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
