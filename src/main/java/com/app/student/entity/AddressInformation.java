package com.app.student.entity;

import javax.persistence.*;

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

    // Local Address
    @Lob
    private String localAddressLine;
    private String localCityOrTaluka;
    private String localDistrict;
    private String localState;
    private String localCountry;
    private String localPinCode;

    // Permanent Address
    @Lob
    private String permanentAddressLine;
    private String permanentCityOrTaluka;
    private String permanentDistrict;
    private String permanentState;
    private String permanentCountry;
    private String permanentPinCode;

    // Association with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
