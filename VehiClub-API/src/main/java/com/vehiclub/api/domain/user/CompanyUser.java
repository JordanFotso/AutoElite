package com.vehiclub.api.domain.user;

import com.vehiclub.api.domain.societe.composite.Societe;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company_users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanyUser extends User {

    @ManyToOne
    @JoinColumn(name = "societe_id")
    private Societe societe;
}
