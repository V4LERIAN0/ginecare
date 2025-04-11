package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.application.usecases.ProcedimientoMedicoUseCase;
import com.clinicasaludmujer.ginecare.application.usecases.ExpedienteUseCase;
import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import com.clinicasaludmujer.ginecare.domain.model.ProcedimientoMedico;
import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import com.clinicasaludmujer.ginecare.interfaces.dto.ProcedimientoMedicoRequest;
import com.clinicasaludmujer.ginecare.interfaces.dto.ProcedimientoMedicoResponse;
import com.clinicasaludmujer.ginecare.interfaces.dto.ExpedienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Gestión de Procedimientos", description = "APIs para gestionar procedimientos")
@RestController
@RequestMapping("/procedimientos")
@CrossOrigin
public class ProcedimientoMedicoController {

    private final ProcedimientoMedicoUseCase procedimientoMedicoUseCase;
    private final ExpedienteUseCase expedienteUseCase;

    public ProcedimientoMedicoController(ProcedimientoMedicoUseCase procedimientoMedicoUseCase,
                                         ExpedienteUseCase expedienteUseCase) {
        this.procedimientoMedicoUseCase = procedimientoMedicoUseCase;
        this.expedienteUseCase = expedienteUseCase;
    }

    @Operation(summary = "Obtener todos los procedimientos", description = "Retorna una lista de procedimientos")
    @GetMapping
    public ResponseEntity<List<ProcedimientoMedicoResponse>> getAllProcedimientos() {
        List<ProcedimientoMedico> procedimientos = procedimientoMedicoUseCase.listarTodos();
        List<ProcedimientoMedicoResponse> responseList = procedimientos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Obtener un procedimiento por su id", description = "Retorna un procedimiento por su id")
    @GetMapping("/{id}")
    public ResponseEntity<ProcedimientoMedicoResponse> getProcedimientoById(@PathVariable Long id) {
        ProcedimientoMedico procedimiento = procedimientoMedicoUseCase.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Procedimiento no encontrado"));
        return ResponseEntity.ok(mapToResponse(procedimiento));
    }

    @Operation(summary = "Crear un procedimiento",
            description = "Creación de un nuevo procedimiento",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<ProcedimientoMedicoResponse> addProcedimiento(
            @Validated @RequestBody ProcedimientoMedicoRequest request) {

        ProcedimientoMedico nuevo = mapToDomain(request);
        ProcedimientoMedico creado = procedimientoMedicoUseCase.crearProcedimiento(nuevo);
        return new ResponseEntity<>(mapToResponse(creado), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un procedimiento",
            description = "Actualización de un procedimiento por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<ProcedimientoMedicoResponse> updateProcedimiento(
            @PathVariable Long id,
            @Validated @RequestBody ProcedimientoMedicoRequest request) {

        ProcedimientoMedico datos = mapToDomain(request);
        ProcedimientoMedico actualizado = procedimientoMedicoUseCase.actualizarProcedimiento(id, datos);
        return ResponseEntity.ok(mapToResponse(actualizado));
    }

    @Operation(summary = "Eliminar un procedimiento",
            description = "Eliminación de un procedimiento por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcedimiento(@PathVariable Long id) {
        procedimientoMedicoUseCase.eliminarProcedimiento(id);
        return ResponseEntity.noContent().build();
    }

    private ProcedimientoMedico mapToDomain(ProcedimientoMedicoRequest req) {
        ProcedimientoMedico pm = new ProcedimientoMedico();
        pm.setNombre(req.getNombre());
        pm.setDescripcion(req.getDescripcion());
        pm.setFechaRealizacion(req.getFechaRealizacion());
        Expediente exp = expedienteUseCase.buscarPorId(req.getExpedienteId())
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado con id = " + req.getExpedienteId()));
        pm.setExpediente(exp);
        return pm;
    }

    private ProcedimientoMedicoResponse mapToResponse(ProcedimientoMedico domain) {
        ProcedimientoMedicoResponse resp = new ProcedimientoMedicoResponse();
        resp.setId(domain.getId());
        resp.setNombre(domain.getNombre());
        resp.setDescripcion(domain.getDescripcion());
        resp.setFechaRealizacion(domain.getFechaRealizacion());
        resp.setExpediente(mapToExpedienteResponse(domain.getExpediente()));
        return resp;
    }

    private ExpedienteResponse mapToExpedienteResponse(Expediente e) {
        if (e == null) return null;
        ExpedienteResponse er = new ExpedienteResponse();
        er.setId(e.getId());
        er.setCodigo(e.getCodigo());
        er.setPaciente(e.getPaciente() != null ? mapToPacienteResponse(e.getPaciente()) : null);
        return er;
    }

    private com.clinicasaludmujer.ginecare.interfaces.dto.PacienteResponse mapToPacienteResponse(Paciente p) {
        com.clinicasaludmujer.ginecare.interfaces.dto.PacienteResponse pr = new com.clinicasaludmujer.ginecare.interfaces.dto.PacienteResponse();
        pr.setId(p.getId());
        pr.setCodigo(p.getCodigo());
        pr.setNombres(p.getNombres());
        pr.setApellidos(p.getApellidos());
        pr.setEmail(p.getEmail());
        pr.setTelefono(p.getTelefono());
        pr.setDireccion(p.getDireccion());
        pr.setFechaNacimiento(p.getFechaNacimiento());
        return pr;
    }
}
