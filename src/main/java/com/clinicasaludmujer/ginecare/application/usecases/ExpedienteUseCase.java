package com.clinicasaludmujer.ginecare.application.usecases;

import com.clinicasaludmujer.ginecare.domain.exceptions.DuplicateExpedienteException;
import com.clinicasaludmujer.ginecare.domain.exceptions.ExpedienteNotFoundException;
import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import com.clinicasaludmujer.ginecare.domain.repository.ExpedienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExpedienteUseCase {

    private final ExpedienteRepository expedienteRepository;

    public ExpedienteUseCase(ExpedienteRepository expedienteRepository) {
        this.expedienteRepository = expedienteRepository;
    }

    public List<Expediente> listarTodos() {
        return expedienteRepository.findAll();
    }

    public Optional<Expediente> buscarPorId(Long id) {
        return expedienteRepository.findById(id);
    }

    public void eliminarExpediente(Long id) {
        if (!expedienteRepository.existsById(id)) {
            throw new ExpedienteNotFoundException("No existe un expediente con el ID = " + id);
        }
        expedienteRepository.deleteById(id);
    }
}
