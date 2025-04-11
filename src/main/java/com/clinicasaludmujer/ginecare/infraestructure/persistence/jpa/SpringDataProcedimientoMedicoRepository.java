package com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa;

import com.clinicasaludmujer.ginecare.domain.model.ProcedimientoMedico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProcedimientoMedicoRepository extends JpaRepository<ProcedimientoMedico, Long> {

}