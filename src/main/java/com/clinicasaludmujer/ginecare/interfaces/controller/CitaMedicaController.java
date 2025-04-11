package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.application.usecases.CitaMedicaUseCase;
import com.clinicasaludmujer.ginecare.application.usecases.PacienteUseCase;
import com.clinicasaludmujer.ginecare.domain.model.CitaMedica;
import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import com.clinicasaludmujer.ginecare.interfaces.dto.CitaMedicaRequest;
import com.clinicasaludmujer.ginecare.interfaces.dto.CitaMedicaResponse;
import com.clinicasaludmujer.ginecare.interfaces.dto.PacienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Gestión de Citas Médicas", description = "APIs para gestionar citas médicas")
@RestController
@RequestMapping("/citas")
@CrossOrigin
public class CitaMedicaController {

    private final CitaMedicaUseCase citaMedicaUseCase;
    private final PacienteUseCase pacienteUseCase;

    public CitaMedicaController(CitaMedicaUseCase citaMedicaUseCase, PacienteUseCase pacienteUseCase) {
        this.citaMedicaUseCase = citaMedicaUseCase;
        this.pacienteUseCase = pacienteUseCase;
    }

    @Operation(summary = "Obtener todas las citas medicas",
            description = "Retorna una lista de citas medicas")
    @GetMapping
    public ResponseEntity<List<CitaMedicaResponse>> getAllCitas() {
        List<CitaMedica> citas = citaMedicaUseCase.listarTodas();
        List<CitaMedicaResponse> responseList = citas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Obtener una cita medica por su id", description = "Retorna una cita medica por su id")
    @GetMapping("/{id}")
    public ResponseEntity<CitaMedicaResponse> getCitaById(@PathVariable Long id) {
        CitaMedica cita = citaMedicaUseCase.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return ResponseEntity.ok(mapToResponse(cita));
    }

    @Operation(summary = "Creación de una cita médica",
            description = "Creación de una nueva cita médica",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<CitaMedicaResponse> addCita(
            @Validated @RequestBody CitaMedicaRequest request) {
        CitaMedica nueva = mapToDomain(request);
        CitaMedica creada = citaMedicaUseCase.crearCitaMedica(nueva);
        return new ResponseEntity<>(mapToResponse(creada), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una cita médica",
            description = "Actualización de una cita médica por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<CitaMedicaResponse> updateCita(
            @PathVariable Long id,
            @Validated @RequestBody CitaMedicaRequest request) {
        CitaMedica datos = mapToDomain(request);
        CitaMedica actualizada = citaMedicaUseCase.actualizarCitaMedica(id, datos);
        return ResponseEntity.ok(mapToResponse(actualizada));
    }

    @Operation(summary = "Eliminar una cita médica",
            description = "Eliminar una cita médica por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        citaMedicaUseCase.eliminarCitaMedica(id);
        return ResponseEntity.noContent().build();
    }

    private CitaMedica mapToDomain(CitaMedicaRequest req) {
        CitaMedica c = new CitaMedica();
        c.setFechaHora(req.getFechaHora());
        c.setMotivo(req.getMotivo());
        c.setEstado(req.getEstado());

        Paciente paciente = pacienteUseCase.buscarPorId(req.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id = " + req.getPacienteId()));
        c.setPaciente(paciente);

        return c;
    }

    private CitaMedicaResponse mapToResponse(CitaMedica domain) {
        CitaMedicaResponse resp = new CitaMedicaResponse();
        resp.setId(domain.getId());
        resp.setFechaHora(domain.getFechaHora());
        resp.setMotivo(domain.getMotivo());
        resp.setEstado(domain.getEstado());

        resp.setPaciente(mapToPacienteResponse(domain.getPaciente()));
        return resp;
    }

    private PacienteResponse mapToPacienteResponse(Paciente p) {
        PacienteResponse pr = new PacienteResponse();
        pr.setId(p.getId());
        pr.setCodigo(p.getCodigo());
        pr.setNombres(p.getNombres());
        pr.setApellidos(p.getApellidos());
        pr.setEmail(p.getEmail());
        pr.setTelefono(p.getTelefono());
        pr.setDireccion(p.getDireccion());
        pr.setFechaNacimiento(p.getFechaNacimiento());
        pr.setExpediente(null);
        return pr;
    }
}
