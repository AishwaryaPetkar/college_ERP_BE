package com.app.student.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


//@Entity
//@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    private String branchName;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private Degree degree;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<AdmissionInformation> studentAdmissions;

    // Getters and Setters
}

