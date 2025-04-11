package com.clinicasaludmujer.ginecare.domain.repository;

import com.clinicasaludmujer.ginecare.domain.model.Consulta;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepository {
    Optional<Consulta> findById(Long id);
    List<Consulta> findAll();
    Consulta save(Consulta consulta);
    void deleteById(Long id);
    boolean existsById(Long id);
}
