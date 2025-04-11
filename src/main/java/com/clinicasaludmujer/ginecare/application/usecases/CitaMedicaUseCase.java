package com.clinicasaludmujer.ginecare.application.usecases;

import com.clinicasaludmujer.ginecare.domain.exceptions.CitaMedicNotFoundException;
import com.clinicasaludmujer.ginecare.domain.repository.CitaMedicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.clinicasaludmujer.ginecare.domain.model.CitaMedica;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CitaMedicaUseCase {

    private final CitaMedicaRepository citaMedicaRepository;

    public CitaMedicaUseCase(CitaMedicaRepository citaMedicaRepository) {
        this.citaMedicaRepository = citaMedicaRepository;
    }

    public List<CitaMedica> listarTodas() {
        return citaMedicaRepository.findAll();
    }

    public Optional<CitaMedica> buscarPorId(Long id) {
        return citaMedicaRepository.findById(id);
    }

    public CitaMedica crearCitaMedica(CitaMedica cita) {
        return citaMedicaRepository.save(cita);
    }

    public CitaMedica actualizarCitaMedica(Long id, CitaMedica datos) {
        CitaMedica existente = citaMedicaRepository.findById(id)
                .orElseThrow(() -> new CitaMedicNotFoundException("Cita no encontrada con id = " + id));

        existente.setFechaHora(datos.getFechaHora());
        existente.setMotivo(datos.getMotivo());
        existente.setEstado(datos.getEstado());
        existente.setPaciente(datos.getPaciente());
        return citaMedicaRepository.save(existente);
    }

    public void eliminarCitaMedica(Long id) {
        if (!citaMedicaRepository.existsById(id)) {
            throw new CitaMedicNotFoundException("No existe cita con ID = " + id);
        }
        citaMedicaRepository.deleteById(id);
    }
}

