package com.clinicasaludmujer.ginecare.infraestructure.persistence.adapter;

import com.clinicasaludmujer.ginecare.domain.model.Receta;
import com.clinicasaludmujer.ginecare.domain.repository.RecetaRepository;
import com.clinicasaludmujer.ginecare.infraestructure.persistence.jpa.SpringDataRecetaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class RecetaRepositoryAdapter implements RecetaRepository {

    private final SpringDataRecetaRepository springDataRecetaRepository;

    public RecetaRepositoryAdapter(SpringDataRecetaRepository springDataRecetaRepository) {
        this.springDataRecetaRepository = springDataRecetaRepository;
    }

    @Override
    public List<Receta> findAll() {
        return springDataRecetaRepository.findAll();
    }

    @Override
    public Optional<Receta> findById(Long id) {
        return springDataRecetaRepository.findById(id);
    }

    @Override
    public Receta save(Receta receta) {
        return springDataRecetaRepository.save(receta);
    }

    @Override
    public void deleteById(Long id) {
        springDataRecetaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataRecetaRepository.existsById(id);
    }
}
