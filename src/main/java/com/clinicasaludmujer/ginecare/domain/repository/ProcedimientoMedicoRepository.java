package com.clinicasaludmujer.ginecare.domain.repository;

import com.clinicasaludmujer.ginecare.domain.model.ProcedimientoMedico;
import java.util.List;
import java.util.Optional;

public interface ProcedimientoMedicoRepository {
    Optional<ProcedimientoMedico> findById(Long id);
    List<ProcedimientoMedico> findAll();
    ProcedimientoMedico save(ProcedimientoMedico procedimientoMedico);
    void deleteById(Long id);
    boolean existsById(Long id);
}
