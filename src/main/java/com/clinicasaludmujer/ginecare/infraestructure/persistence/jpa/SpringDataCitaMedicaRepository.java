package com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa;

import com.clinicasaludmujer.ginecare.domain.model.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCitaMedicaRepository extends JpaRepository<CitaMedica, Long> {
}
