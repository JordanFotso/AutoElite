package com.vehiclub.api.repositories;

import com.vehiclub.api.domain.societe.composite.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocieteRepository extends JpaRepository<Societe, Long> {
    List<Societe> findByParentIsNull();
}
