package com.vehiclub.api.dto;

import lombok.Data;

@Data
public class CompanyUserRegistrationRequest {
    private String email;
    private String password;
    private Long societeId;
    private String contactPersonName;
    private String phone;
    private String address;
    private String companyRegistrationNumber;
    private String website;
    private String companyBankAccountNumber;
}
