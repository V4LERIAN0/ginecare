package com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa;

import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SpringDataPacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCodigo(String codigo);
    List<Paciente> findByNombres(String nombres);
    List<Paciente> findByApellidos(String apellidos);
}

