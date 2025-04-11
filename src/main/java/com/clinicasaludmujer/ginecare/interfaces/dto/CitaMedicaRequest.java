package com.clinicasaludmujer.ginecare.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.clinicasaludmujer.ginecare.domain.enums.EstadoCita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CitaMedicaRequest {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El campo motivo no puede estar vacío o nulo")
    private String motivo;

    private EstadoCita estado = EstadoCita.PENDIENTE;

    @NotNull(message = "El pacienteId es obligatorio")
    private Long pacienteId;
}
