package com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa;

import com.clinicasaludmujer.ginecare.domain.model.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
}
