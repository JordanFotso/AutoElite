package com.vehiclub.api.domain.societe.composite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("FEUILLE")
@NoArgsConstructor
public class SocieteFeuille extends Societe {

    public SocieteFeuille(String nom) {
        super(nom);
    }

    @Override
    public void afficher(int indentation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append("  ");
        }
        System.out.println(sb.toString() + "- " + nom);
    }
}
