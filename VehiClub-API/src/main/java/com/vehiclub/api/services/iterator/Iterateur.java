package com.vehiclub.api.services.iterator;

import com.vehiclub.api.domain.Vehicule;

public interface Iterateur {
    boolean aSuivant();
    Vehicule suivant();
}
