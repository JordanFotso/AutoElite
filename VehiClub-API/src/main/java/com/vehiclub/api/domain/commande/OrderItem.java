package com.vehiclub.api.domain.commande;

import com.vehiclub.api.domain.Vehicule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    private int quantity;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_item_options", joinColumns = @JoinColumn(name = "order_item_id"))
    @Column(name = "option_id")
    private List<String> selectedOptionsIds; // IDs métier des options sélectionnées

    public OrderItem(Vehicule vehicule, int quantity, List<String> selectedOptionsIds) {
        this.vehicule = vehicule;
        this.quantity = quantity;
        this.selectedOptionsIds = selectedOptionsIds;
    }
}
