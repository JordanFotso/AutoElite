package com.vehiclub.api.dto;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
