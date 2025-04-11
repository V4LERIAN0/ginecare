package com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa;

import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataExpedienteRepository extends JpaRepository<Expediente, Long> {
    Optional<Expediente> findByCodigo(String codigo);
}