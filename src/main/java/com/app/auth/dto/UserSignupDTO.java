package com.app.auth.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSignupDTO {
	@NotBlank
	private String fullName;
	@Email
	@NotBlank
	private String email;	
	@NotBlank
	@Size(min=6)
	private String password;
	@NotBlank
	private LocalDate DOB;
	@NotBlank
	private String gender;

}
