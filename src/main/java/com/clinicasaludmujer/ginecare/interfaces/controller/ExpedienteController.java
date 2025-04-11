package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.application.usecases.ExpedienteUseCase;
import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import com.clinicasaludmujer.ginecare.interfaces.dto.ExpedienteResponse;
import com.clinicasaludmujer.ginecare.interfaces.dto.PacienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Gestión de Expedientes", description = "APIs para gestionar expedientes")
@RestController
@RequestMapping("/expedientes")
@CrossOrigin
public class ExpedienteController {

    private final ExpedienteUseCase expedienteUseCase;

    public ExpedienteController(ExpedienteUseCase expedienteUseCase) {
        this.expedienteUseCase = expedienteUseCase;
    }

    @Operation(summary = "Obtener todos los expedientes", description = "Retorna una lista de expedientes")
    @GetMapping
    public ResponseEntity<List<ExpedienteResponse>> getAllExpedientes() {
        List<Expediente> expedientes = expedienteUseCase.listarTodos();
        List<ExpedienteResponse> responseList = expedientes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Obtener expediente por su id", description = "Retorno de un expediente por su id")
    @GetMapping("/{id}")
    public ResponseEntity<ExpedienteResponse> getExpedienteById(@PathVariable Long id) {
        Expediente exp = expedienteUseCase.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado"));
        return ResponseEntity.ok(mapToResponse(exp));
    }

    @Operation(summary = "Eliminar un expediente",
            description = "Eliminación de un expediente",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpediente(@PathVariable Long id) {
        expedienteUseCase.eliminarExpediente(id);
        return ResponseEntity.noContent().build();
    }

    private ExpedienteResponse mapToResponse(Expediente domain) {
        if (domain == null) return null;
        ExpedienteResponse resp = new ExpedienteResponse();
        resp.setId(domain.getId());
        resp.setCodigo(domain.getCodigo());
        resp.setPaciente(domain.getPaciente() != null ? mapToPacienteResponseWithoutExpediente(domain.getPaciente()) : null);
        return resp;
    }

    private PacienteResponse mapToPacienteResponseWithoutExpediente(Paciente paciente) {
        if (paciente == null) return null;
        return new PacienteResponse(
                paciente.getId(),
                paciente.getCodigo(),
                paciente.getNombres(),
                paciente.getApellidos(),
                paciente.getEmail(),
                paciente.getTelefono(),
                paciente.getDireccion(),
                paciente.getFechaNacimiento(),
                null
        );
    }
}
