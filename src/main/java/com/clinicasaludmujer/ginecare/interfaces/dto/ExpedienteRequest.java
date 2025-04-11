package com.clinicasaludmujer.ginecare.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteRequest {

    @NotBlank(message = "El campo código no puede estar vacío o nulo")
    @Size(min = 4, max = 15, message = "Debe tener entre 4 y 15 caracteres")
    private String codigo;
    @NotNull
    private Long pacienteId;

}
