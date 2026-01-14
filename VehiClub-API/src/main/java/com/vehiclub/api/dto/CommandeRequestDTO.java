package com.vehiclub.api.dto;

import com.vehiclub.api.domain.commande.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class CommandeRequestDTO {
    private List<OrderItem> items;
    private String typeCommande;
    private String paysLivraison;
}
