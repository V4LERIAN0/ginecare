package com.clinicasaludmujer.ginecare.domain.exceptions;

public class DiagnosticoNotFoundException extends RuntimeException {
    public DiagnosticoNotFoundException(String message) {
        super(message);
    }
}
