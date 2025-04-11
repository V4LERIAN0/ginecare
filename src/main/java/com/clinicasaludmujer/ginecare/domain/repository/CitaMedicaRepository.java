package com.clinicasaludmujer.ginecare.domain.repository;

import com.clinicasaludmujer.ginecare.domain.model.CitaMedica;
import java.util.List;
import java.util.Optional;

public interface CitaMedicaRepository {
    Optional<CitaMedica> findById(Long id);
    List<CitaMedica> findAll();
    CitaMedica save(CitaMedica citaMedica);
    void deleteById(Long id);
    boolean existsById(Long id);
}
