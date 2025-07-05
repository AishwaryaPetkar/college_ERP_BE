package com.app.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.student.entity.AcademicRecords;

public interface StudentAcademicRecordsRepository extends JpaRepository<AcademicRecords, Long> {

}
