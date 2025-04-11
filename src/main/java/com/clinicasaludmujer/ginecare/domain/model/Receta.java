package com.clinicasaludmujer.ginecare.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Recetas")
public class Receta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dosis;
    private String frecuencia;
    private Integer duracionDias;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "procedimiento_id", nullable = false)
    private ProcedimientoMedico procedimiento;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

}
