package com.clinicasaludmujer.ginecare.interfaces.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteResponse {

    private Long id;
    private String codigo;
    private PacienteResponse paciente;
}
