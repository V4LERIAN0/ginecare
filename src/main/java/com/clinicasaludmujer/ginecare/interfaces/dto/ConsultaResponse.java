package com.clinicasaludmujer.ginecare.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaResponse {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaConsulta;
    private Double peso;
    private String presionArterial;
    private Double estatura;
    private String dilatacionPupila;
    private String evaluacionPulmonar;
    private String observaciones;

    private ExpedienteResponse expediente;
    private List<DiagnosticoResponse> diagnostico;
    private List<RecetaResponse> receta;
}
