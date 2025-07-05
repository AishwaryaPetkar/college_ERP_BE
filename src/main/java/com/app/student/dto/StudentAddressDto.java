package com.app.student.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAddressDto {
	private boolean isSameAddress;

    // For same address
    private AddressDetail address;

    // For different addresses
    private AddressDetail localAddress;
    private AddressDetail permanentAddress;

    @Data
    public static class AddressDetail {
        private String addressLine;
        private String cityTaluka;
        private String district;
        private String state;
        private String country;
        private String pinCode;
    }
	 
	 
}
