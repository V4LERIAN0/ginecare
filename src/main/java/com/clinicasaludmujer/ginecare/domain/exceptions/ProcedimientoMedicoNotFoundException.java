package com.clinicasaludmujer.ginecare.domain.exceptions;

public class ProcedimientoMedicoNotFoundException extends RuntimeException {
    public ProcedimientoMedicoNotFoundException(String message) {
        super(message);
    }
}