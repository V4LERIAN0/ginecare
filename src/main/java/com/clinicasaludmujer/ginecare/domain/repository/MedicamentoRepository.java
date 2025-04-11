package com.clinicasaludmujer.ginecare.domain.repository;

import com.clinicasaludmujer.ginecare.domain.model.Medicamento;
import java.util.List;
import java.util.Optional;

public interface MedicamentoRepository {
    Optional<Medicamento> findById(Long id);
    List<Medicamento> findAll();
    Medicamento save(Medicamento medicamento);
    Optional<Medicamento> findByCodigo(String codigo);
    void deleteById(Long id);
    boolean existsById(Long id);
}
