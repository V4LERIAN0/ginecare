package com.clinicasaludmujer.ginecare.domain.exceptions;


public class ExpedienteNotFoundException extends RuntimeException {
    public ExpedienteNotFoundException(String message) {
        super(message);
    }
}
