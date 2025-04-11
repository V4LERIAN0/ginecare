package com.clinicasaludmujer.ginecare.infraestructure.persistence.adapter;

import com.clinicasaludmujer.ginecare.domain.model.ProcedimientoMedico;
import com.clinicasaludmujer.ginecare.domain.repository.ProcedimientoMedicoRepository;
import com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa.SpringDataProcedimientoMedicoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ProcedimientoMedicoRepositoryAdapter implements ProcedimientoMedicoRepository {

    private final SpringDataProcedimientoMedicoRepository springDataRepository;

    public ProcedimientoMedicoRepositoryAdapter(SpringDataProcedimientoMedicoRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<ProcedimientoMedico> findById(Long id) {
        return springDataRepository.findById(id);
    }

    @Override
    public List<ProcedimientoMedico> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public ProcedimientoMedico save(ProcedimientoMedico procedimientoMedico) {
        return springDataRepository.save(procedimientoMedico);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataRepository.existsById(id);
    }
}
