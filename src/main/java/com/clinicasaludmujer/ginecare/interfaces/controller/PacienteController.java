package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.application.usecases.PacienteUseCase;
import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import com.clinicasaludmujer.ginecare.interfaces.dto.ExpedienteResponse;
import com.clinicasaludmujer.ginecare.interfaces.dto.PacienteRequest;
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

@Tag(name = "Gestión de Pacientes", description = "APIs para gestionar pacientes")
@RestController
@RequestMapping("/pacientes")
@CrossOrigin
public class PacienteController {

    private final PacienteUseCase pacienteUseCase;

    public PacienteController(PacienteUseCase pacienteUseCase) {
        this.pacienteUseCase = pacienteUseCase;
    }

    @Operation(summary = "Obtener todos los pacientes", description = "Retorna una lista de pacientes")
    @GetMapping
    public ResponseEntity<List<PacienteResponse>> getAllPacientes() {
        List<Paciente> pacientes = pacienteUseCase.listarTodos();
        List<PacienteResponse> responseList = pacientes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Obtener un paciente por su id", description = "Retorna un paciente por su id")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> getPacienteById(@PathVariable Long id) {
        Paciente paciente = pacienteUseCase.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        return ResponseEntity.ok(mapToResponse(paciente));
    }

    @Operation(summary = "Crear un paciente",
            description = "Creación de un nuevo paciente",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<PacienteResponse> addPaciente(
            @Validated @RequestBody PacienteRequest request) {
        Paciente nuevo = mapToDomain(request);
        Paciente creado = pacienteUseCase.crearPaciente(nuevo);
        return new ResponseEntity<>(mapToResponse(creado), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un paciente",
            description = "Actualización de un paciente por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> updatePaciente(
            @PathVariable Long id,
            @Validated @RequestBody PacienteRequest request) {
        Paciente data = mapToDomain(request);
        Paciente actualizado = pacienteUseCase.actualizarPaciente(id, data);
        return ResponseEntity.ok(mapToResponse(actualizado));
    }

    @Operation(summary = "Eliminar un paciente",
            description = "Eliminar un paciente por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteUseCase.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    private Paciente mapToDomain(PacienteRequest req) {
        Paciente p = new Paciente();
        p.setNombres(req.getNombres());
        p.setApellidos(req.getApellidos());
        p.setEmail(req.getEmail());
        p.setTelefono(req.getTelefono());
        p.setDireccion(req.getDireccion());
        p.setFechaNacimiento(req.getFechaNacimiento());
        return p;
    }

    private ExpedienteResponse mapToExpedienteResponse(Expediente e) {
        if (e == null) return null;
        ExpedienteResponse er = new ExpedienteResponse();
        er.setId(e.getId());
        er.setCodigo(e.getCodigo());
        er.setPaciente(null);
        return er;
    }

    private PacienteResponse mapToResponse(Paciente domain) {
        return new PacienteResponse(
                domain.getId(),
                domain.getCodigo(),
                domain.getNombres(),
                domain.getApellidos(),
                domain.getEmail(),
                domain.getTelefono(),
                domain.getDireccion(),
                domain.getFechaNacimiento(),
                mapToExpedienteResponse(domain.getExpediente())
        );
    }
}
