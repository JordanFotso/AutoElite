package com.vehiclub.api.services;

import com.vehiclub.api.dto.CompanyUserRegistrationRequest;
import com.vehiclub.api.dto.CustomerRegistrationRequest;
import com.vehiclub.api.domain.user.CompanyUser;
import com.vehiclub.api.domain.user.Customer;
import com.vehiclub.api.domain.user.User;
import com.vehiclub.api.repositories.UserRepository;
import com.vehiclub.api.repositories.CustomerRepository;
import com.vehiclub.api.repositories.CompanyUserRepository;
import com.vehiclub.api.services.SocieteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository; // Injecter le CustomerRepository
    private final CompanyUserRepository companyUserRepository; // Injecter le CompanyUserRepository
    private final PasswordEncoder passwordEncoder;
    private final SocieteService societeService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           CompanyUserRepository companyUserRepository,
                           PasswordEncoder passwordEncoder,
                           SocieteService societeService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.companyUserRepository = companyUserRepository;
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
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setDob(request.getDob());
        customer.setBankAccountNumber(request.getBankAccountNumber());
        customer.setRoles(Set.of("ROLE_CUSTOMER"));

        customerRepository.save(customer);

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
        companyUser.setContactPersonName(request.getContactPersonName());
        companyUser.setPhone(request.getPhone());
        companyUser.setAddress(request.getAddress());
        companyUser.setCompanyRegistrationNumber(request.getCompanyRegistrationNumber());
        companyUser.setWebsite(request.getWebsite());
        companyUser.setCompanyBankAccountNumber(request.getCompanyBankAccountNumber());
        companyUser.setRoles(Set.of("ROLE_COMPANY_USER"));

        if (request.getSocieteId() != null) {
            societeService.getSocieteById(request.getSocieteId()).ifPresent(companyUser::setSociete);
        } else {
            return ResponseEntity.badRequest().body("Error: Societe ID is required for a company user.");
        }

        companyUserRepository.save(companyUser);

        return ResponseEntity.ok("Company user registered successfully!");
    }

    @Override
    public ResponseEntity<?> getUserDetails(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
}
