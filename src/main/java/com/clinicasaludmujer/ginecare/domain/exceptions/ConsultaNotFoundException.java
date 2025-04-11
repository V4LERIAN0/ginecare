package com.clinicasaludmujer.ginecare.domain.exceptions;

public class ConsultaNotFoundException extends RuntimeException {
    public ConsultaNotFoundException(String message) {
        super(message);
    }
}

