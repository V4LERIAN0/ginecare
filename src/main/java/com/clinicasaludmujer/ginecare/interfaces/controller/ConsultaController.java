package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.application.usecases.ConsultaUseCase;
import com.clinicasaludmujer.ginecare.application.usecases.ExpedienteUseCase;
import com.clinicasaludmujer.ginecare.domain.model.Consulta;
import com.clinicasaludmujer.ginecare.domain.model.Expediente;
import com.clinicasaludmujer.ginecare.domain.model.Paciente;
import com.clinicasaludmujer.ginecare.domain.model.Diagnostico;
import com.clinicasaludmujer.ginecare.domain.model.Receta;
import com.clinicasaludmujer.ginecare.interfaces.dto.ConsultaRequest;
import com.clinicasaludmujer.ginecare.interfaces.dto.ConsultaResponse;
import com.clinicasaludmujer.ginecare.interfaces.dto.ExpedienteResponse;
import com.clinicasaludmujer.ginecare.interfaces.dto.PacienteResponse;
import com.clinicasaludmujer.ginecare.interfaces.dto.DiagnosticoResponse;
import com.clinicasaludmujer.ginecare.interfaces.dto.RecetaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Consultas", description = "APIs para gestionar consultas")
@RestController
@RequestMapping("/consultas")
@CrossOrigin
public class ConsultaController {

    private final ConsultaUseCase consultaUseCase;
    private final ExpedienteUseCase expedienteUseCase;

    public ConsultaController(ConsultaUseCase consultaUseCase,
                              ExpedienteUseCase expedienteUseCase) {
        this.consultaUseCase = consultaUseCase;
        this.expedienteUseCase = expedienteUseCase;
    }

    @Operation(summary = "Obtener todas las consutlas", description = "Retorna una lista de consultas")
    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> getAllConsultas() {
        List<Consulta> consultas = consultaUseCase.listarTodas();
        List<ConsultaResponse> responseList = consultas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Obtener consulta por su id", description = "Retorno de una consulta por su id")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> getConsultaById(@PathVariable Long id) {
        Consulta consulta = consultaUseCase.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
        return ResponseEntity.ok(mapToResponse(consulta));
    }

    @Operation(summary = "Crear una consulta",
            description = "Creación de una nueva consulta",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<ConsultaResponse> addConsulta(
            @Validated @RequestBody ConsultaRequest request) {

        Consulta nueva = mapToDomain(request);

        Consulta creada = consultaUseCase.crearConsulta(nueva);

        return new ResponseEntity<>(mapToResponse(creada), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una consulta",
            description = "Actualizar una consulta por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponse> updateConsulta(
            @PathVariable Long id,
            @Validated @RequestBody ConsultaRequest request) {

        Consulta datos = mapToDomain(request);
        Consulta actualizada = consultaUseCase.actualizarConsulta(id, datos);
        return ResponseEntity.ok(mapToResponse(actualizada));
    }

    @Operation(summary = "Eliminar una consulta",
            description = "Eliminar una consulta por su id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable Long id) {
        consultaUseCase.eliminarConsulta(id);
        return ResponseEntity.noContent().build();
    }

    private Consulta mapToDomain(ConsultaRequest req) {
        Consulta c = new Consulta();
        c.setFechaConsulta(req.getFechaConsulta());
        c.setPeso(req.getPeso());
        c.setPresionArterial(req.getPresionArterial());
        c.setEstatura(req.getEstatura());
        c.setDilatacionPupila(req.getDilatacionPupila());
        c.setEvaluacionPulmonar(req.getEvaluacionPulmonar());
        c.setObservaciones(req.getObservaciones());

        Expediente exp = expedienteUseCase.buscarPorId(req.getExpedienteId())
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado con id = " + req.getExpedienteId()));
        c.setExpediente(exp);

        return c;
    }

    private ConsultaResponse mapToResponse(Consulta domain) {
        ConsultaResponse resp = new ConsultaResponse();
        resp.setId(domain.getId());
        resp.setFechaConsulta(domain.getFechaConsulta());
        resp.setPeso(domain.getPeso());
        resp.setPresionArterial(domain.getPresionArterial());
        resp.setEstatura(domain.getEstatura());
        resp.setDilatacionPupila(domain.getDilatacionPupila());
        resp.setEvaluacionPulmonar(domain.getEvaluacionPulmonar());
        resp.setObservaciones(domain.getObservaciones());
        resp.setExpediente(domain.getExpediente() != null ? mapToExpedienteResponse(domain.getExpediente()) : null);

        if (domain.getDiagnosticos() != null) {
            resp.setDiagnostico(domain.getDiagnosticos().stream()
                    .map(this::mapDiagnostico)
                    .collect(Collectors.toList()));
        }
        if (domain.getRecetas() != null) {
            resp.setReceta(domain.getRecetas().stream()
                    .map(this::mapReceta)
                    .collect(Collectors.toList()));
        }
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

    private PacienteResponse mapToPacienteResponse(Paciente paciente) {
        if (paciente == null) return null;
        PacienteResponse pr = new PacienteResponse();
        pr.setId(paciente.getId());
        pr.setCodigo(paciente.getCodigo());
        pr.setNombres(paciente.getNombres());
        pr.setApellidos(paciente.getApellidos());
        pr.setEmail(paciente.getEmail());
        pr.setTelefono(paciente.getTelefono());
        pr.setDireccion(paciente.getDireccion());
        pr.setFechaNacimiento(paciente.getFechaNacimiento());
        return pr;
    }

    private DiagnosticoResponse mapDiagnostico(Diagnostico d) {
        DiagnosticoResponse dr = new DiagnosticoResponse();
        dr.setId(d.getId());
        dr.setDescripcion(d.getDescripcion());
        dr.setFecha(d.getFecha());
        return dr;
    }

    private RecetaResponse mapReceta(Receta r) {
        RecetaResponse rr = new RecetaResponse();
        rr.setId(r.getId());
        rr.setDosis(r.getDosis());
        rr.setFrecuencia(r.getFrecuencia());
        rr.setDuracionDias(r.getDuracionDias());
        return rr;
    }
}
