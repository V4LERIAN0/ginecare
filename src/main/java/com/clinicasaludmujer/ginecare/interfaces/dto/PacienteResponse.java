package com.clinicasaludmujer.ginecare.interfaces.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponse {
    private Long id;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;
    private ExpedienteResponse expediente;
}

