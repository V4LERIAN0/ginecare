package com.clinicasaludmujer.ginecare.domain.repository;

import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository {

    Optional<Paciente> findById(Long id);

    Optional<Paciente> findByCodigo(String codigo);

    List<Paciente> findByNombres(String nombres);

    List<Paciente> findByApellidos(String apellidos);

    List<Paciente> findAll();

    Paciente save(Paciente paciente);

    void deleteById(Long id);

    boolean existsById(Long id);
}
