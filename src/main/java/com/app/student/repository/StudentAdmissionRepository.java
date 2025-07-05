package com.app.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.student.entity.AdmissionInformation;

public interface StudentAdmissionRepository extends JpaRepository<AdmissionInformation, Long> {

}
