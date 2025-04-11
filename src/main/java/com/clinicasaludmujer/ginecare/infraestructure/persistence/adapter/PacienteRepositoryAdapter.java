package com.clinicasaludmujer.ginecare.infraestructure.persistence.adapter;

import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import com.clinicasaludmujer.ginecare.domain.repository.PacienteRepository;
import com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa.SpringDataPacienteRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class PacienteRepositoryAdapter implements PacienteRepository {

    private final SpringDataPacienteRepository springDataPacienteRepository;

    public PacienteRepositoryAdapter(SpringDataPacienteRepository springDataPacienteRepository) {
        this.springDataPacienteRepository = springDataPacienteRepository;
    }

    @Override
    public Optional<Paciente> findById(Long id) {
        return springDataPacienteRepository.findById(id);
    }

    @Override
    public Optional<Paciente> findByCodigo(String codigo) {
        return springDataPacienteRepository.findByCodigo(codigo);
    }

    @Override
    public List<Paciente> findByNombres(String nombres) {
        return springDataPacienteRepository.findByNombres(nombres);
    }

    @Override
    public List<Paciente> findByApellidos(String apellidos) {
        return springDataPacienteRepository.findByApellidos(apellidos);
    }

    @Override
    public List<Paciente> findAll() {
        return springDataPacienteRepository.findAll();
    }

    @Override
    public Paciente save(Paciente paciente) {
        return springDataPacienteRepository.save(paciente);
    }

    @Override
    public void deleteById(Long id) {
        springDataPacienteRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataPacienteRepository.existsById(id);
    }
}
