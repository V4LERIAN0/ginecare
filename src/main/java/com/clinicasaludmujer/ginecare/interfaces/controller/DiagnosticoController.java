package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.application.usecases.ConsultaUseCase;
import com.clinicasaludmujer.ginecare.application.usecases.DiagnosticoUseCase;
import com.clinicasaludmujer.ginecare.application.usecases.ProcedimientoMedicoUseCase;
import com.clinicasaludmujer.ginecare.domain.model.Diagnostico;
import com.clinicasaludmujer.ginecare.interfaces.dto.DiagnosticoRequest;
import com.clinicasaludmujer.ginecare.interfaces.dto.DiagnosticoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Gestión de Diagnóstico", description = "APIs para gestionar diagnosticos")
@RestController
@RequestMapping("/diagnosticos")
@RequiredArgsConstructor
@CrossOrigin
public class DiagnosticoController {

    private final DiagnosticoUseCase diagnosticoUseCase;
    private final ConsultaUseCase consultaUseCase;
    private final ProcedimientoMedicoUseCase procedimientoMedicoUseCase;

    @Operation(summary = "Obtener todas los diagnosticos", description = "Retorna una lista de diagnosticos")
    @GetMapping
    public ResponseEntity<List<DiagnosticoResponse>> getAllDiagnosticos() {
        List<Diagnostico> diagnosticos = diagnosticoUseCase.listarTodos();
        List<DiagnosticoResponse> responses = diagnosticos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtener diagnostico por su id", description = "Retorno de un diagnostico por su id")
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoResponse> getDiagnosticoById(@PathVariable Long id) {
        Diagnostico diagnostico = diagnosticoUseCase.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado"));
        return ResponseEntity.ok(mapToResponse(diagnostico));
    }

    @Operation(summary = "Crear un diagnostico",
            description = "Creación de un nuevo diagnostico",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<DiagnosticoResponse> createDiagnostico(@Validated @RequestBody DiagnosticoRequest request) {
        Diagnostico diagnostico = mapToDomain(request);
        Diagnostico creado = diagnosticoUseCase.crearDiagnostico(diagnostico);
        return new ResponseEntity<>(mapToResponse(creado), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un diagnostico",
            description = "Actualización de un diagnostico por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticoResponse> updateDiagnostico(@PathVariable Long id, @Validated @RequestBody DiagnosticoRequest request) {
        Diagnostico diagnostico = mapToDomain(request);
        Diagnostico actualizado = diagnosticoUseCase.actualizarDiagnostico(id, diagnostico);
        return ResponseEntity.ok(mapToResponse(actualizado));
    }

    @Operation(summary = "Eliminar un diagnostico",
            description = "Eliminación de un diagnostico por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnostico(@PathVariable Long id) {
        diagnosticoUseCase.eliminarDiagnostico(id);
        return ResponseEntity.noContent().build();
    }

    private Diagnostico mapToDomain(DiagnosticoRequest req) {
        Diagnostico d = new Diagnostico();
        d.setDescripcion(req.getDescripcion());
        d.setFecha(req.getFecha());

        d.setConsulta(
                consultaUseCase.buscarPorId(req.getConsultaId())
                        .orElseThrow(() -> new RuntimeException("Consulta no encontrada con id = " + req.getConsultaId()))
        );

        d.setProcedimiento(
                procedimientoMedicoUseCase.buscarPorId(req.getProcedimientoId())
                        .orElseThrow(() -> new RuntimeException("Procedimiento médico no encontrado con id = " + req.getProcedimientoId()))
        );

        return d;
    }

    private DiagnosticoResponse mapToResponse(Diagnostico d) {
        DiagnosticoResponse dr = new DiagnosticoResponse();
        dr.setId(d.getId());
        dr.setDescripcion(d.getDescripcion());
        dr.setFecha(d.getFecha());
        return dr;
    }
}
