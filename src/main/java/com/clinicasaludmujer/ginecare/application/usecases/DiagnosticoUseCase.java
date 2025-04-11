package com.clinicasaludmujer.ginecare.application.usecases;

import com.clinicasaludmujer.ginecare.domain.exceptions.DiagnosticoNotFoundException;
import com.clinicasaludmujer.ginecare.domain.model.Diagnostico;
import com.clinicasaludmujer.ginecare.domain.repository.DiagnosticoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiagnosticoUseCase {

    private final DiagnosticoRepository diagnosticoRepository;

    public DiagnosticoUseCase(DiagnosticoRepository diagnosticoRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
    }

    public List<Diagnostico> listarTodos() {
        return diagnosticoRepository.findAll();
    }

    public Optional<Diagnostico> buscarPorId(Long id) {
        return diagnosticoRepository.findById(id);
    }

    public Diagnostico crearDiagnostico(Diagnostico diagnostico) {
        return diagnosticoRepository.save(diagnostico);
    }

    public Diagnostico actualizarDiagnostico(Long id, Diagnostico datos) {
        Diagnostico existente = diagnosticoRepository.findById(id)
                .orElseThrow(() -> new DiagnosticoNotFoundException("Diagnóstico no encontrado con id = " + id));
        existente.setDescripcion(datos.getDescripcion());
        existente.setFecha(datos.getFecha());
        return diagnosticoRepository.save(existente);
    }

    public void eliminarDiagnostico(Long id) {
        if (!diagnosticoRepository.existsById(id)) {
            throw new DiagnosticoNotFoundException("Diagnóstico no encontrado con id = " + id);
        }
        diagnosticoRepository.deleteById(id);
    }
}
