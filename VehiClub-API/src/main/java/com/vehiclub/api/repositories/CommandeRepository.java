package com.vehiclub.api.repositories;

import com.vehiclub.api.domain.user.User;
import com.vehiclub.api.domain.commande.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByUser(User user);
}
