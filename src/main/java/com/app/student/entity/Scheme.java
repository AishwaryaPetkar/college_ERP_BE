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
//@Table(name = "schemes")
public class Scheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String academicYear;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private Degree degree;

    @OneToMany(mappedBy = "scheme", cascade = CascadeType.ALL)
    private List<AdmissionInformation> studentAdmissions;

    // Getters and Setters
}

