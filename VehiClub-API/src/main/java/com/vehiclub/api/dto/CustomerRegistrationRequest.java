package com.vehiclub.api.dto;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String dob; // Date of Birth as String for simplicity in form
    private String bankAccountNumber;
}
