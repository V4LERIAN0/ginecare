package com.clinicasaludmujer.ginecare.domain.repository;

import com.clinicasaludmujer.ginecare.domain.model.Receta;
import java.util.List;
import java.util.Optional;

public interface RecetaRepository {
    List<Receta> findAll();
    Optional<Receta> findById(Long id);
    Receta save(Receta receta);
    void deleteById(Long id);
    boolean existsById(Long id);

}
