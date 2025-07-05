package com.app.student.service;

import com.app.auth.entities.User;
import com.app.auth.repository.UserRepository;
import com.app.student.dto.StudentAddressDto;
import com.app.student.entity.AddressInformation;
import com.app.student.repository.StudentAddressRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentAddressServiceImpl implements StudentAddressService  {

    @Autowired
    private StudentAddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void saveStudentAddress(StudentAddressDto addressDTO) {
        User user = userRepository.findById(addressDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + addressDTO.getUserId()));

        if (addressDTO.isSameAddress()) {
            AddressInformation address = mapAddressWithType(addressDTO.getAddress(), "BOTH", true, user);
            addressRepository.save(address);
        } else {
            AddressInformation local = mapAddressWithType(addressDTO.getLocalAddress(), "LOCAL", false, user);
            AddressInformation permanent = mapAddressWithType(addressDTO.getPermanentAddress(), "PERMANENT", false, user);
            addressRepository.save(local);
            addressRepository.save(permanent);
        }
    }

    private AddressInformation mapAddressWithType(StudentAddressDto dto, String type, boolean isSame, User user) {
        AddressInformation address = modelMapper.map(dto, AddressInformation.class);
        address.setType(type);
        address.setSameAddress(isSame);
        address.setUser(user);
        return address;
    }

	
}
