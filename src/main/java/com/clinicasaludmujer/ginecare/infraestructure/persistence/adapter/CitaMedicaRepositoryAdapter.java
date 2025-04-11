package com.clinicasaludmujer.ginecare.infraestructure.persistence.adapter;

import com.clinicasaludmujer.ginecare.domain.model.CitaMedica;
import com.clinicasaludmujer.ginecare.domain.repository.CitaMedicaRepository;
import com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa.SpringDataCitaMedicaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class CitaMedicaRepositoryAdapter implements CitaMedicaRepository {

    private final SpringDataCitaMedicaRepository springDataCitaMedicaRepository;

    public CitaMedicaRepositoryAdapter(SpringDataCitaMedicaRepository springDataCitaMedicaRepository) {
        this.springDataCitaMedicaRepository = springDataCitaMedicaRepository;
    }

    @Override
    public Optional<CitaMedica> findById(Long id) {
        return springDataCitaMedicaRepository.findById(id);
    }

    @Override
    public List<CitaMedica> findAll() {
        return springDataCitaMedicaRepository.findAll();
    }

    @Override
    public CitaMedica save(CitaMedica citaMedica) {
        return springDataCitaMedicaRepository.save(citaMedica);
    }

    @Override
    public void deleteById(Long id) {
        springDataCitaMedicaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataCitaMedicaRepository.existsById(id);
    }
}
