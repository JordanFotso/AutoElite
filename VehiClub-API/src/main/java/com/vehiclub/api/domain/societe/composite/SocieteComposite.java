package com.vehiclub.api.domain.societe.composite;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@DiscriminatorValue("COMPOSITE")
@Getter
@Setter
@NoArgsConstructor
public class SocieteComposite extends Societe {

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("filiales-parent") // Gère la référence principale pour éviter les boucles de sérialisation
    private List<Societe> filiales = new ArrayList<>();

    public SocieteComposite(String nom) {
        super(nom);
    }

    @Override
    public void afficher(int indentation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append("  ");
        }
        System.out.println(sb.toString() + "+ " + nom);
        for (Societe filiale : filiales) {
            filiale.afficher(indentation + 1);
        }
    }

    @Override
    public void add(Societe societe) {
        filiales.add(societe);
        societe.setParent(this); // Assurez-vous de définir le parent
    }

    @Override
    public void remove(Societe societe) {
        filiales.remove(societe);
        societe.setParent(null); // Dissocier le parent
    }

    @Override
    public List<Societe> getFiliales() {
        return Collections.unmodifiableList(filiales);
    }
}
