package com.app.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.student.entity.AddressInformation;

public interface StudentAddressRepository extends JpaRepository<AddressInformation, Long> {

}
