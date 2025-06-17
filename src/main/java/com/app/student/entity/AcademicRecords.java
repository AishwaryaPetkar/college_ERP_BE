package com.app.student.entity;

import javax.persistence.*;

import com.app.auth.entities.User;
import lombok.*;

@Entity
@Table(name = "academic_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AcademicRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Educational Qualification Fields
    private String examPassed;
    private String boardOrUniversity;
    private String instituteOrSchool;
    private String rollNumber;
    private String passingYear;
    private String resultType;
    private String percentage;
    private String stream;
    private String medium;
    private String mode;
    private String gapReason;
    private String gapYear;
    private String resultStatus;
    private String examRank;
    private String degreeName;
    private String branchName;
    private String durationInYear;
    private String discipline;
    private String examScheme;
    private String enrollmentNumber;
    private String markObtained;
    private String outOfMarked;

    // Document Uploads
    private String xeroxOfDegreeMarksheet;  // e.g., "UPLOADED"
    private String degreeMarksheet;         // e.g., "UPLOADED"

    // Association with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
