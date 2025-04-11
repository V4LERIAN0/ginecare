package com.clinicasaludmujer.ginecare.interfaces.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecetaResponse {

    private Long id;
    private String dosis;
    private String frecuencia;
    private Integer duracionDias;

}
