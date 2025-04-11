package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.application.usecases.MedicamentoUseCase;
import com.clinicasaludmujer.ginecare.domain.exceptions.MedicamentoNotFoundException;
import com.clinicasaludmujer.ginecare.domain.model.Medicamento;
import com.clinicasaludmujer.ginecare.interfaces.dto.MedicamentoRequest;
import com.clinicasaludmujer.ginecare.interfaces.dto.MedicamentoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Gestión de Medicamentos", description = "APIs para gestionar medicamentos")
@RestController
@RequestMapping("/medicamentos")
@CrossOrigin
public class MedicamentoController {

    private final MedicamentoUseCase medicamentoUseCase;

    public MedicamentoController(MedicamentoUseCase medicamentoUseCase) {
        this.medicamentoUseCase = medicamentoUseCase;
    }

    @Operation(summary = "Obtener todos los medicamentos", description = "Retorna una lista de medicamentos")
    @GetMapping
    public ResponseEntity<List<MedicamentoResponse>> listarTodos() {
        List<Medicamento> medicamentos = medicamentoUseCase.listarTodos();
        List<MedicamentoResponse> medicamentoResponses = medicamentos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(medicamentoResponses);
    }

    @Operation(summary = "Obtener medicamento por su id", description = "Retorna un medicamento por su id")
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> findById(@PathVariable Long id) {
        Medicamento medicamento = medicamentoUseCase.buscarPorId(id)
                .orElseThrow(() -> new MedicamentoNotFoundException("Medicamento no encontrado"));

        return ResponseEntity.status(HttpStatus.OK).body(mapToResponse(medicamento));
    }

    @Operation(summary = "Crear de un medicamento",
            description = "Creación de un nuevo medicamento",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<MedicamentoResponse> addMedicamento(@Validated @RequestBody MedicamentoRequest req){
        Medicamento nuevo = mapToDomain(req);
        Medicamento creado = medicamentoUseCase.guardarMedicamento(nuevo);

        return new ResponseEntity<>(mapToResponse(creado), HttpStatus.CREATED);

    }

    @Operation(summary = "Actualizar un medicamento",
            description = "Actualiación de un medicamento por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> updateMedicamento(@PathVariable Long id, @Validated @RequestBody MedicamentoRequest req){
        Medicamento data = mapToDomain(req);
        Medicamento actualizado = medicamentoUseCase.actualizarMedicamento(id, data);
        return ResponseEntity.ok(mapToResponse(actualizado));
    }

    @Operation(summary = "Eliminar un medicamento",
            description = "Eliminación de un medicamento por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Long id){
        medicamentoUseCase.eliminarMedicamento(id);
        return ResponseEntity.noContent().build();
    }

    private Medicamento mapToDomain(MedicamentoRequest req) {
        Medicamento medicamento = new Medicamento();
        medicamento.setFechaVencimiento(req.getFechaVencimiento());
        medicamento.setNombre(req.getNombre());
        medicamento.setDescripcion(req.getDescripcion());
        medicamento.setStock(req.getStock());

        return medicamento;
    }

    private MedicamentoResponse mapToResponse(Medicamento domain) {
        MedicamentoResponse resp = new MedicamentoResponse();
        resp.setId(domain.getId());
        resp.setCodigo(domain.getCodigo());
        resp.setNombre(domain.getNombre());
        resp.setDescripcion(domain.getDescripcion());
        resp.setStock(domain.getStock());
        resp.setFechaVencimiento(domain.getFechaVencimiento());

        return resp;
    }
}
