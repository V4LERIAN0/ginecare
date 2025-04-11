package com.clinicasaludmujer.ginecare.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pacientes")
public class Paciente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código interno del paciente
    @NotBlank
    @Size(min = 4, max = 15)
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String nombres;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String apellidos;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 15)
    private String telefono;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String direccion;

    @NotNull
    @Column(name = "fechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Expediente expediente;

    public void validarCodigo() {
        if (codigo == null || !codigo.startsWith("PAC")) {
            throw new IllegalArgumentException("El código del paciente debe iniciar con 'PAC'.");
        }
    }
}

