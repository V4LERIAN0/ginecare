package com.clinicasaludmujer.ginecare.interfaces.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequest {

    @NotBlank(message = "El campo nombres no puede estar vacío o nulo")
    private String nombres;

    @NotBlank(message = "El campo apellidos no puede estar vacío o nulo")
    private String apellidos;

    @NotBlank(message = "El email no puede ser nulo o vacío")
    @Email
    private String email;

    @NotBlank(message = "El campo teléfono no puede estar vacío o nulo")
    private String telefono;

    @NotBlank(message = "El campo dirección no puede estar vacío o nulo")
    private String direccion;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate fechaNacimiento;

}
