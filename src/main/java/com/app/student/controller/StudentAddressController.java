package com.app.student.controller;

import com.app.student.dto.StudentAddressDto;
import com.app.student.service.StudentAddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentAddressController {

    @Autowired
    private StudentAddressService addressService;

    @PostMapping("/save-student-address")
    public ResponseEntity<String> saveAddress(@RequestBody StudentAddressDto addressDTO) {
        addressService.saveStudentAddress(addressDTO);
        return ResponseEntity.ok("Address saved successfully");
    }
}
