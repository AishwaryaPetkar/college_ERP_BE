package com.app.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
	@Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
