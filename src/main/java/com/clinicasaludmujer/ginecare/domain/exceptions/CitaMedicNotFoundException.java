package com.clinicasaludmujer.ginecare.domain.exceptions;

public class CitaMedicNotFoundException extends RuntimeException{
    public CitaMedicNotFoundException(String message) {
        super(message);
    }
}
