package com.clinicasaludmujer.ginecare.interfaces.dto;

import com.clinicasaludmujer.ginecare.domain.model.Diagnostico;
import com.clinicasaludmujer.ginecare.domain.model.Receta;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimientoMedicoResponse {

    private Long id;
    private String nombre;
    private String descripcion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaRealizacion;
    private ExpedienteResponse expediente;
}
