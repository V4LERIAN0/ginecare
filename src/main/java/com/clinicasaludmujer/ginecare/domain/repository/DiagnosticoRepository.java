package com.clinicasaludmujer.ginecare.domain.repository;

import com.clinicasaludmujer.ginecare.domain.model.Diagnostico;
import java.util.List;
import java.util.Optional;

public interface DiagnosticoRepository {
    List<Diagnostico> findAll();
    Optional<Diagnostico> findById(Long id);
    Diagnostico save(Diagnostico diagnostico);
    void deleteById(Long id);
    boolean existsById(Long id);
}
