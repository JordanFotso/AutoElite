package com.vehiclub.api.services;

import com.vehiclub.api.dto.CompanyUserRegistrationRequest;
import com.vehiclub.api.dto.CustomerRegistrationRequest;
import com.vehiclub.api.domain.user.CompanyUser;
import com.vehiclub.api.domain.user.Customer;
import com.vehiclub.api.repositories.UserRepository;
import com.vehiclub.api.services.SocieteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SocieteService societeService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SocieteService societeService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.societeService = societeService;
    }

    @Override
    public ResponseEntity<?> registerCustomer(CustomerRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setRoles(Set.of("ROLE_CUSTOMER"));

        userRepository.save(customer);

        return ResponseEntity.ok("Customer registered successfully!");
    }

    @Override
    public ResponseEntity<?> registerCompanyUser(CompanyUserRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        CompanyUser companyUser = new CompanyUser();
        companyUser.setEmail(request.getEmail());
        companyUser.setPassword(passwordEncoder.encode(request.getPassword()));
        companyUser.setRoles(Set.of("ROLE_COMPANY_USER"));

        if (request.getSocieteId() != null) {
            societeService.getSocieteById(request.getSocieteId()).ifPresent(companyUser::setSociete);
        } else {
            return ResponseEntity.badRequest().body("Error: Societe ID is required for a company user.");
        }

        userRepository.save(companyUser);

        return ResponseEntity.ok("Company user registered successfully!");
    }
}
