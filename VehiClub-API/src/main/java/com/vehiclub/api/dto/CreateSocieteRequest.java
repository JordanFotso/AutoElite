package com.vehiclub.api.dto;

import lombok.Data;

@Data
public class CreateSocieteRequest {
    private String nom;
    private Long parentId;
}
