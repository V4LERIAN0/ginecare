package com.clinicasaludmujer.ginecare.application.usecases;

import com.clinicasaludmujer.ginecare.domain.exceptions.RecetaNotFoundException;
import com.clinicasaludmujer.ginecare.domain.model.Receta;
import com.clinicasaludmujer.ginecare.domain.repository.RecetaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RecetaUseCase {

    private final RecetaRepository recetaRepository;

    public RecetaUseCase(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public List<Receta> listarTodos() {
        return recetaRepository.findAll();
    }

    public Optional<Receta> buscarPorId(Long id) {
        return recetaRepository.findById(id);
    }

    public Receta crearReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    public Receta actualizarReceta(Long id, Receta datos) {
        Receta existente = recetaRepository.findById(id)
                .orElseThrow(() -> new RecetaNotFoundException("Receta no encontrada con id = " + id));
        existente.setDosis(datos.getDosis());
        existente.setFrecuencia(datos.getFrecuencia());
        existente.setDuracionDias(datos.getDuracionDias());
        return recetaRepository.save(existente);
    }

    public void eliminarReceta(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new RecetaNotFoundException("Receta no encontrada con id = " + id);
        }
        recetaRepository.deleteById(id);
    }
}
