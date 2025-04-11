package com.clinicasaludmujer.ginecare.interfaces.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecetaRequest {

    private String dosis;
    private String frecuencia;
    private Integer duracionDias;

    @NotNull(message = "El id de consulta es obligatorio")
    private Long consultaId;

    @NotNull(message = "El id del procedimiento es obligatorio")
    private Long procedimientoId;

    private Long medicamentoId;
}
