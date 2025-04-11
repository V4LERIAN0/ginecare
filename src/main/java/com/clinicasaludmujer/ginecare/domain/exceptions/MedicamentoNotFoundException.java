package com.clinicasaludmujer.ginecare.domain.exceptions;

public class MedicamentoNotFoundException extends RuntimeException {
    public MedicamentoNotFoundException(String message) {
        super(message);
    }
}
