//package com.app.faculty.entities;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import com.app.student.entity.Course;
//
//@Entity
//@Table(name = "faculty_courses")
//public class FacultyCourse {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "faculty_id", nullable = false)
//    private Faculty faculty;
//
//    @ManyToOne
//    @JoinColumn(name = "course_id", nullable = false)
//    private Course course;
//
//    private int semester;
//    private int year;
//
//    // Getters and setters
//}
//
