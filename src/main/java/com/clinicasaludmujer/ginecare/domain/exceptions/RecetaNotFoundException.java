package com.clinicasaludmujer.ginecare.domain.exceptions;

public class RecetaNotFoundException extends RuntimeException {
    public RecetaNotFoundException(String message) {
        super(message);
    }
}
