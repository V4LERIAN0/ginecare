package com.clinicasaludmujer.ginecare.infraestructure.persistence.adapter;

import com.clinicasaludmujer.ginecare.domain.model.Consulta;
import com.clinicasaludmujer.ginecare.domain.repository.ConsultaRepository;
import com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa.SpringDataConsultaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ConsultaRepositoryAdapter implements ConsultaRepository {

    private final SpringDataConsultaRepository springDataConsultaRepository;

    public ConsultaRepositoryAdapter(SpringDataConsultaRepository repo) {
        this.springDataConsultaRepository = repo;
    }

    @Override
    public Optional<Consulta> findById(Long id) {
        return springDataConsultaRepository.findById(id);
    }

    @Override
    public List<Consulta> findAll() {
        return springDataConsultaRepository.findAll();
    }

    @Override
    public Consulta save(Consulta consulta) {
        return springDataConsultaRepository.save(consulta);
    }

    @Override
    public void deleteById(Long id) {
        springDataConsultaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataConsultaRepository.existsById(id);
    }
}
