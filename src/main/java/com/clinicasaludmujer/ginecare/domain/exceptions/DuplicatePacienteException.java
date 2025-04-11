package com.clinicasaludmujer.ginecare.domain.exceptions;

public class DuplicatePacienteException extends RuntimeException {
    public DuplicatePacienteException(String message) {
        super(message);
    }
}
