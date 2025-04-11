package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.application.usecases.RecetaUseCase;
import com.clinicasaludmujer.ginecare.application.usecases.ConsultaUseCase;
import com.clinicasaludmujer.ginecare.application.usecases.ProcedimientoMedicoUseCase;
import com.clinicasaludmujer.ginecare.application.usecases.MedicamentoUseCase;
import com.clinicasaludmujer.ginecare.domain.model.Receta;
import com.clinicasaludmujer.ginecare.domain.model.Consulta;
import com.clinicasaludmujer.ginecare.domain.model.ProcedimientoMedico;
import com.clinicasaludmujer.ginecare.domain.model.Medicamento;
import com.clinicasaludmujer.ginecare.interfaces.dto.RecetaRequest;
import com.clinicasaludmujer.ginecare.interfaces.dto.RecetaResponse;
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

@Tag(name = "Gestión de Recetas", description = "APIs para gestionar recetas")
@RestController
@RequestMapping("/recetas")
@RequiredArgsConstructor
@CrossOrigin
public class RecetaController {

    private final RecetaUseCase recetaUseCase;
    private final ConsultaUseCase consultaUseCase;
    private final ProcedimientoMedicoUseCase procedimientoUseCase;
    private final MedicamentoUseCase medicamentoUseCase;

    @Operation(summary = "Obtener todos las recetas", description = "Retorna una lista de recetas")
    @GetMapping
    public ResponseEntity<List<RecetaResponse>> getAllRecetas() {
        List<Receta> recetas = recetaUseCase.listarTodos();
        List<RecetaResponse> responses = recetas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtener una receta por su id", description = "Retorna de una receta por su id")
    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponse> getRecetaById(@PathVariable Long id) {
        Receta receta = recetaUseCase.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        return ResponseEntity.ok(mapToResponse(receta));
    }

    @Operation(summary = "Crear una receta",
            description = "Creación  de una nueva receta",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<RecetaResponse> createReceta(@Validated @RequestBody RecetaRequest request) {
        Receta receta = mapToDomain(request);
        Receta creado = recetaUseCase.crearReceta(receta);
        return new ResponseEntity<>(mapToResponse(creado), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una receta por su id",
            description = "Actualización de una receta por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<RecetaResponse> updateReceta(@PathVariable Long id, @Validated @RequestBody RecetaRequest request) {
        Receta receta = mapToDomain(request);
        Receta actualizado = recetaUseCase.actualizarReceta(id, receta);
        return ResponseEntity.ok(mapToResponse(actualizado));
    }

    @Operation(summary = "Eliminar una receta por su id",
            description = "Eliminación de una receta por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id) {
        recetaUseCase.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }

    private Receta mapToDomain(RecetaRequest req) {
        Receta r = new Receta();
        r.setDosis(req.getDosis());
        r.setFrecuencia(req.getFrecuencia());
        r.setDuracionDias(req.getDuracionDias());

        Consulta consulta = consultaUseCase.buscarPorId(req.getConsultaId())
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada con id = " + req.getConsultaId()));
        r.setConsulta(consulta);

        ProcedimientoMedico procedimiento = procedimientoUseCase.buscarPorId(req.getProcedimientoId())
                .orElseThrow(() -> new RuntimeException("Procedimiento médico no encontrado con id = " + req.getProcedimientoId()));
        r.setProcedimiento(procedimiento);

        if (req.getMedicamentoId() != null) {
            Medicamento medicamento = medicamentoUseCase.buscarPorId(req.getMedicamentoId())
                    .orElse(null);
            r.setMedicamento(medicamento);
        }
        return r;
    }

    private RecetaResponse mapToResponse(Receta r) {
        RecetaResponse rr = new RecetaResponse();
        rr.setId(r.getId());
        rr.setDosis(r.getDosis());
        rr.setFrecuencia(r.getFrecuencia());
        rr.setDuracionDias(r.getDuracionDias());

        return rr;
    }
}

