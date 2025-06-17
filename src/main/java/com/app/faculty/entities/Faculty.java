//package com.app.faculty.entities;
//
//
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MapsId;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import com.app.auth.entities.User;
//import com.app.student.entity.Branch;
//
//@Entity
//@Table(name = "faculty")
//public class Faculty {
//
//    @Id
//    @Column(name = "user_id")
//    private Long userId;  // FK → users.id
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Column(name = "employee_id", unique = true, nullable = false)
//    private String employeeId;
//
//    @ManyToOne
//    @JoinColumn(name = "branch_id")
//    private Branch branchId;
//
//    @Column(nullable = false)
//    private String designation;
//
//    @Column(name = "phone_no.", length = 10)
//    private String phone;
//
//    // Getters and setters
//}
//
