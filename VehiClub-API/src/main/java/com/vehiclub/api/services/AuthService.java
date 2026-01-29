package com.vehiclub.api.services;

import com.vehiclub.api.dto.CompanyUserRegistrationRequest;
import com.vehiclub.api.dto.CustomerRegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> registerCustomer(CustomerRegistrationRequest request);
    ResponseEntity<?> registerCompanyUser(CompanyUserRegistrationRequest request);
}
