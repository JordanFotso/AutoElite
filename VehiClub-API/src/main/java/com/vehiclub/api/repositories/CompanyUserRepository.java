package com.vehiclub.api.repositories;

import com.vehiclub.api.domain.user.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
}
