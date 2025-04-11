package com.clinicasaludmujer.ginecare.infraestructure.persistence.adapter;

import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import com.clinicasaludmujer.ginecare.domain.repository.ExpedienteRepository;
import com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa.SpringDataExpedienteRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ExpedienteRepositoryAdapter implements ExpedienteRepository {

    private final SpringDataExpedienteRepository springDataExpedienteRepository;

    public ExpedienteRepositoryAdapter(SpringDataExpedienteRepository repo) {
        this.springDataExpedienteRepository = repo;
    }

    @Override
    public Optional<Expediente> findById(Long id) {
        return springDataExpedienteRepository.findById(id);
    }

    @Override
    public Optional<Expediente> findByCodigo(String codigo) {
        return springDataExpedienteRepository.findByCodigo(codigo);
    }

    @Override
    public List<Expediente> findAll() {
        return springDataExpedienteRepository.findAll();
    }

    @Override
    public Expediente save(Expediente expediente) {
        return springDataExpedienteRepository.save(expediente);
    }

    @Override
    public void deleteById(Long id) {
        springDataExpedienteRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataExpedienteRepository.existsById(id);
    }
}
