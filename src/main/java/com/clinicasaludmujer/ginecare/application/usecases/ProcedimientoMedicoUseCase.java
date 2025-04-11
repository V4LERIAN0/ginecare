package com.clinicasaludmujer.ginecare.application.usecases;

import com.clinicasaludmujer.ginecare.domain.exceptions.ProcedimientoMedicoNotFoundException;
import com.clinicasaludmujer.ginecare.domain.model.ProcedimientoMedico;
import com.clinicasaludmujer.ginecare.domain.repository.ProcedimientoMedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProcedimientoMedicoUseCase {

    private final ProcedimientoMedicoRepository procedimientoMedicoRepository;

    public ProcedimientoMedicoUseCase(ProcedimientoMedicoRepository procedimientoMedicoRepository) {
        this.procedimientoMedicoRepository = procedimientoMedicoRepository;
    }

    public List<ProcedimientoMedico> listarTodos() {
        return procedimientoMedicoRepository.findAll();
    }

    public Optional<ProcedimientoMedico> buscarPorId(Long id) {
        return procedimientoMedicoRepository.findById(id);
    }

    public ProcedimientoMedico crearProcedimiento(ProcedimientoMedico procedimiento) {
        return procedimientoMedicoRepository.save(procedimiento);
    }

    public ProcedimientoMedico actualizarProcedimiento(Long id, ProcedimientoMedico datos) {
        ProcedimientoMedico existente = procedimientoMedicoRepository.findById(id)
                .orElseThrow(() -> new ProcedimientoMedicoNotFoundException(
                        "Procedimiento no encontrado con id = " + id));

        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setFechaRealizacion(datos.getFechaRealizacion());
        existente.setExpediente(datos.getExpediente());

        return procedimientoMedicoRepository.save(existente);
    }

    public void eliminarProcedimiento(Long id) {
        if (!procedimientoMedicoRepository.existsById(id)) {
            throw new ProcedimientoMedicoNotFoundException("No existe procedimiento con ID = " + id);
        }
        procedimientoMedicoRepository.deleteById(id);
    }
}
