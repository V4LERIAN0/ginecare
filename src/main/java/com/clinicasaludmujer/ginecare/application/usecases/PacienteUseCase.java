package com.clinicasaludmujer.ginecare.application.usecases;

import com.clinicasaludmujer.ginecare.domain.exceptions.DuplicatePacienteException;
import com.clinicasaludmujer.ginecare.domain.exceptions.PacienteNotFoundException;
import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import com.clinicasaludmujer.ginecare.domain.repository.ExpedienteRepository;
import com.clinicasaludmujer.ginecare.domain.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class PacienteUseCase {

    private final PacienteRepository pacienteRepository;
    private final ExpedienteRepository expedienteRepository;

    public PacienteUseCase(
            PacienteRepository pacienteRepository,
            ExpedienteRepository expedienteRepository
    ) {
        this.pacienteRepository = pacienteRepository;
        this.expedienteRepository = expedienteRepository;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente crearPaciente(Paciente paciente) {
        if (paciente.getCodigo() == null || paciente.getCodigo().isBlank()) {
            paciente.setCodigo(generarCodigoPacienteUnico());
        }
        // Validar
        paciente.validarCodigo();
        verificarDuplicadoPaciente(paciente.getCodigo());

        Expediente exp = paciente.getExpediente();
        if (exp == null) {
            exp = new Expediente();
            exp.setCodigo(generarCodigoExpedienteUnico());
            exp.validarCodigoExpediente();
            exp.setPaciente(paciente);
            paciente.setExpediente(exp);
        } else {
            if (exp.getCodigo() == null || exp.getCodigo().isBlank()) {
                exp.setCodigo(generarCodigoExpedienteUnico());
            }
            exp.validarCodigoExpediente();
            exp.setPaciente(paciente);
        }

        Paciente saved = pacienteRepository.save(paciente);

        return recargarPacienteConExpediente(saved.getId());
    }

    public Paciente actualizarPaciente(Long id, Paciente pacienteConDatos) {
        Paciente existente = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException(
                        "Paciente no encontrado con id = " + id));

        String codigoAntiguo = existente.getCodigo();

        existente.setNombres(pacienteConDatos.getNombres());
        existente.setApellidos(pacienteConDatos.getApellidos());
        existente.setEmail(pacienteConDatos.getEmail());
        existente.setTelefono(pacienteConDatos.getTelefono());
        existente.setDireccion(pacienteConDatos.getDireccion());
        existente.setFechaNacimiento(pacienteConDatos.getFechaNacimiento());

        existente.setCodigo(codigoAntiguo); // Por si hubo algún cambio
        Paciente updated = pacienteRepository.save(existente);

        return recargarPacienteConExpediente(updated.getId());
    }

    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new PacienteNotFoundException("No existe paciente con ID = " + id);
        }
        pacienteRepository.deleteById(id);
    }

    private void verificarDuplicadoPaciente(String codigoPaciente) {
        if (pacienteRepository.findByCodigo(codigoPaciente).isPresent()) {
            throw new DuplicatePacienteException(
                    "El código [" + codigoPaciente + "] ya existe");
        }
    }

    private String generarCodigoPacienteUnico() {
        String code;
        do {
            code = generarCodigoPacienteCorto();
        } while (pacienteRepository.findByCodigo(code).isPresent());
        return code;
    }
    private String generarCodigoPacienteCorto() {
        int randomNumber = new Random().nextInt(9000) + 1000;
        return "PAC" + randomNumber;
    }

    private String generarCodigoExpedienteUnico() {
        String code;
        do {
            code = generarCodigoExpedienteCorto();
        } while (expedienteRepository.findByCodigo(code).isPresent());
        return code;
    }
    private String generarCodigoExpedienteCorto() {
        int randomNumber = new Random().nextInt(9000) + 1000;
        return "EXP" + randomNumber;
    }

    private Paciente recargarPacienteConExpediente(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException(
                        "No se pudo recargar el paciente con ID = " + id));
    }
}
