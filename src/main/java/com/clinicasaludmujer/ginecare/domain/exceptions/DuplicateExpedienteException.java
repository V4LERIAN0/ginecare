package com.clinicasaludmujer.ginecare.domain.exceptions;

public class DuplicateExpedienteException extends RuntimeException {
    public DuplicateExpedienteException(String message) {
        super(message);
    }
}
