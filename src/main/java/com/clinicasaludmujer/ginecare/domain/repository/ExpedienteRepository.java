package com.clinicasaludmujer.ginecare.domain.repository;

import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import java.util.Optional;
import java.util.List;

public interface ExpedienteRepository {

    Optional<Expediente> findById(Long id);
    Optional<Expediente> findByCodigo(String codigo);
    List<Expediente> findAll();
    Expediente save(Expediente expediente);
    void deleteById(Long id);
    boolean existsById(Long id);
}


