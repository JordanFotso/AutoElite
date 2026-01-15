package com.vehiclub.api.domain.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Specifications {
    private String engine;
    private String power;
    private String acceleration;
    private String topSpeed;
    public String getEngine(){
        return this.engine;
    }
}

