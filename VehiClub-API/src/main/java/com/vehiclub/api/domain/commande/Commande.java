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
    private double montantInitial; // Prix de base avant taxes et remises
    private double montantTotal;   // Prix final
    private String paysLivraison;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @Enumerated(EnumType.STRING)
    private StatutCommande status;

    public Commande(Vehicule vehicule, double montantInitial, String paysLivraison) {
        this.vehicule = vehicule;
        this.montantInitial = montantInitial;
        this.paysLivraison = paysLivraison;
        this.dateCommande = LocalDateTime.now();
        this.status = StatutCommande.EN_COURS;
    }

    // La Template Method
    public final void calculerMontantTotal() {
        double prixBase = this.montantInitial;
        double taxes = calculerTaxes(prixBase, this.paysLivraison);
        double frais = calculerFrais(prixBase); // "Hook" pour les frais supplémentaires

        this.montantTotal = prixBase + taxes + frais;
    }

    protected abstract double calculerTaxes(double prixBase, String paysLivraison);

    // "Hook" - une étape optionnelle avec une implémentation par défaut
    protected double calculerFrais(double prixBase) {
        return 0.0;
    }

    public abstract String getTypeCommande();
    public abstract boolean validerCommande();

    // Enum pour le statut de la commande
    public enum StatutCommande {
        EN_COURS, VALIDEE, LIVREE, ANNULEE
    }
}
