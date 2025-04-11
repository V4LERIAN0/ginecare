package com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa;

import com.clinicasaludmujer.ginecare.domain.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataConsultaRepository extends JpaRepository<Consulta, Long> {
}
