package com.clinicasaludmujer.ginecare.application.usecases;

import com.clinicasaludmujer.ginecare.domain.exceptions.ConsultaNotFoundException;
import com.clinicasaludmujer.ginecare.domain.model.Consulta;
import com.clinicasaludmujer.ginecare.domain.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConsultaUseCase {

    private final ConsultaRepository consultaRepository;

    public ConsultaUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public Consulta crearConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public Consulta actualizarConsulta(Long id, Consulta datos) {
        Consulta existente = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNotFoundException(
                        "Consulta no encontrada con id = " + id));

        existente.setFechaConsulta(datos.getFechaConsulta());
        existente.setPeso(datos.getPeso());
        existente.setPresionArterial(datos.getPresionArterial());
        existente.setEstatura(datos.getEstatura());
        existente.setDilatacionPupila(datos.getDilatacionPupila());
        existente.setEvaluacionPulmonar(datos.getEvaluacionPulmonar());
        existente.setObservaciones(datos.getObservaciones());

        return consultaRepository.save(existente);
    }

    public void eliminarConsulta(Long id) {
        if (!consultaRepository.existsById(id)) {
            throw new ConsultaNotFoundException(
                    "No existe Consulta con ID = " + id);
        }
        consultaRepository.deleteById(id);
    }
}
