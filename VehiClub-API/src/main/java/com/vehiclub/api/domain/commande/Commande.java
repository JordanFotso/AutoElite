package com.vehiclub.api.domain.commande;

import com.vehiclub.api.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_commande")
@Data
@NoArgsConstructor
public abstract class Commande {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "commande_id")
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime dateCommande;
    private double montantTotal;
    private String paysLivraison;

    @Enumerated(EnumType.STRING)
    private StatutCommande status;

    public Commande(List<OrderItem> items, User user, String paysLivraison) {
        this.items = items;
        this.user = user;
        this.paysLivraison = paysLivraison;
        this.dateCommande = LocalDateTime.now();
        this.status = StatutCommande.EN_COURS;
    }

    public abstract String getTypeCommande();
    public abstract boolean validerCommande();

    public enum StatutCommande {
        EN_COURS, VALIDEE, LIVREE, ANNULEE
    }
}
