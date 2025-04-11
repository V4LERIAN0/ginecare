package com.clinicasaludmujer.ginecare.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoRequest {

    @NotBlank(message = "El campo nombre no puede estar vacio o nulo")
    private String nombre;

    @NotBlank(message = "El campo descripcion no puede estar vacio o nulo")
    private String descripcion;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;

    @NotNull(message = "El campo stock no puede ser nulo")
    private int stock;
}
