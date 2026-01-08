package com.vehiclub.api.domain.commande;

import com.vehiclub.api.domain.Vehicule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_commande")
@Data
@NoArgsConstructor
public abstract class Commande {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateCommande;
    private double montantTotal;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @Enumerated(EnumType.STRING)
    private StatutCommande status;

    public Commande(Vehicule vehicule, double montantTotal) {
        this.vehicule = vehicule;
        this.montantTotal = montantTotal;
        this.dateCommande = LocalDateTime.now();
        this.status = StatutCommande.EN_COURS;
    }

    public abstract String getTypeCommande();
    public abstract boolean validerCommande(); // Logique de validation spécifique au type de commande
    public abstract void calculerTotalAvecTaxes(String paysLivraison); // Logique de calcul des taxes

    // Enum pour le statut de la commande
    public enum StatutCommande {
        EN_COURS, VALIDEE, LIVREE, ANNULEE
    }
}
