package com.vehiclub.api.dto;

import lombok.Data;

@Data
public class CompanyUserRegistrationRequest {
    private String email;
    private String password;
    private Long societeId;
}
