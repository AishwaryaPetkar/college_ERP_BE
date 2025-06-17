package com.app.student.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



//@Entity
//@Table(name = "degrees")
public class Degree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
    private List<Branch> branches;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
    private List<Scheme> schemes;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
    private List<AdmissionInformation> studentAdmissions;

    // Getters and Setters
}

