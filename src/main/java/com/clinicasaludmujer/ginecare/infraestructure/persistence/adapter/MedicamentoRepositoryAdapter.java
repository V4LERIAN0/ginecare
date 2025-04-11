package com.clinicasaludmujer.ginecare.infraestructure.persistence.adapter;

import com.clinicasaludmujer.ginecare.domain.model.Medicamento;
import com.clinicasaludmujer.ginecare.domain.repository.MedicamentoRepository;
import com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa.SpringDataMedicamentoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicamentoRepositoryAdapter implements MedicamentoRepository {

    private final SpringDataMedicamentoRepository springDataMedicamentoRepository;

    public MedicamentoRepositoryAdapter(SpringDataMedicamentoRepository repo) {
        this.springDataMedicamentoRepository = repo;
    }

    @Override
    public Optional<Medicamento> findById(Long id) {
        return springDataMedicamentoRepository.findById(id);
    }

    @Override
    public List<Medicamento> findAll() {
        return springDataMedicamentoRepository.findAll();
    }

    @Override
    public Medicamento save(Medicamento medicamento) {
        return springDataMedicamentoRepository.save(medicamento);
    }

    @Override
    public Optional<Medicamento> findByCodigo(String codigo) {
        return springDataMedicamentoRepository.findByCodigo(codigo);
    }

    @Override
    public void deleteById(Long id) {
        springDataMedicamentoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataMedicamentoRepository.existsById(id);
    }
}
