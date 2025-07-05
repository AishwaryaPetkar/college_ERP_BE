package com.app.student.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.app.auth.entities.User;
import lombok.*;

@Entity
@Table(name = "address_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type; // "LOCAL" or "PERMANENT"
	private boolean isSameAddress;

	private String addressLine;
	private String cityTaluka;
	private String district;
	private String state;
	private String country;
	private String pinCode;

	// Association with User
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
