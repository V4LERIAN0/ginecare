package com.clinicasaludmujer.ginecare.domain.exceptions;

public class DuplicateMedicamentoException extends RuntimeException {
    public DuplicateMedicamentoException(String message) {
        super(message);
    }
}
