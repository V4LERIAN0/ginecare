package com.clinicasaludmujer.ginecare.interfaces.controller;

import com.clinicasaludmujer.ginecare.interfaces.dto.LoginRequest;
import com.clinicasaludmujer.ginecare.interfaces.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoint para login")
@RestController
public class AuthDocController {

    //Método para poder sutenticar el usuaario
    @Operation(summary = "Login y obtención de token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}