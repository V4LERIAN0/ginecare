package com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa;

import com.clinicasaludmujer.ginecare.domain.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataMedicamentoRepository extends JpaRepository<Medicamento, Long> {
    Optional<Medicamento> findByCodigo(String codigo);
}
