package com.clinicasaludmujer.ginecare.infraestructure.persistence.adapter;

import com.clinicasaludmujer.ginecare.domain.model.Diagnostico;
import com.clinicasaludmujer.ginecare.domain.repository.DiagnosticoRepository;
import com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa.SpringDataDiagnosticoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DiagnosticoRepositoryAdapter implements DiagnosticoRepository {

    private final SpringDataDiagnosticoRepository springDataDiagnosticoRepository;

    public DiagnosticoRepositoryAdapter(SpringDataDiagnosticoRepository springDataDiagnosticRepository) {
        this.springDataDiagnosticoRepository = springDataDiagnosticRepository;
    }

    @Override
    public List<Diagnostico> findAll() {
        return springDataDiagnosticoRepository.findAll();
    }

    @Override
    public Optional<Diagnostico> findById(Long id) {
        return springDataDiagnosticoRepository.findById(id);
    }

    @Override
    public Diagnostico save(Diagnostico diagnostico) {
        return springDataDiagnosticoRepository.save(diagnostico);
    }

    @Override
    public void deleteById(Long id) {
        springDataDiagnosticoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataDiagnosticoRepository.existsById(id);
    }
}
