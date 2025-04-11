package com.clinicasaludmujer.ginecare.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaRequest {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaConsulta;

    private Double peso;
    private String presionArterial;
    private Double estatura;
    private String dilatacionPupila;
    private String evaluacionPulmonar;
    private String observaciones;

    @NotNull
    private Long expedienteId;
}
