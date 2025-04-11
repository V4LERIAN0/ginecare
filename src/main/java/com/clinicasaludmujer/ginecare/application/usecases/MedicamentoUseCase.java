package com.clinicasaludmujer.ginecare.application.usecases;

import com.clinicasaludmujer.ginecare.domain.exceptions.DuplicateMedicamentoException;
import com.clinicasaludmujer.ginecare.domain.exceptions.MedicamentoNotFoundException;
import com.clinicasaludmujer.ginecare.domain.model.Medicamento;
import com.clinicasaludmujer.ginecare.domain.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class MedicamentoUseCase {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoUseCase(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> listarTodos() {
        return medicamentoRepository.findAll();
    }

    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    public Medicamento guardarMedicamento(Medicamento medicamento) {
        if (medicamento.getCodigo() == null || medicamento.getCodigo().isBlank()) {
            medicamento.setCodigo(generarCodigoMedicamentoUnico());
        }
        verificarDuplicadoMedicamento(medicamento.getCodigo());
        return medicamentoRepository.save(medicamento);
    }

    public Medicamento actualizarMedicamento(Long id, Medicamento datos) {
        Medicamento existente = medicamentoRepository.findById(id)
                .orElseThrow(() -> new MedicamentoNotFoundException("Medicamento no encontrado con id: " + id));

        String codigoOriginal = existente.getCodigo();

        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setStock(datos.getStock());
        existente.setFechaVencimiento(datos.getFechaVencimiento());

        existente.setCodigo(codigoOriginal);

        return medicamentoRepository.save(existente);
    }

    public void eliminarMedicamento(Long id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new MedicamentoNotFoundException("Medicamento no encontrado");
        }
        medicamentoRepository.deleteById(id);
    }

    private void verificarDuplicadoMedicamento(String codigoMedicamento) {
        if (medicamentoRepository.findByCodigo(codigoMedicamento).isPresent()) {
            throw new DuplicateMedicamentoException("El código [" + codigoMedicamento + "] ya existe");
        }
    }

    private String generarCodigoMedicamentoUnico() {
        String code;
        do {
            code = generarCodigoMedicamentoCorto();
        } while (medicamentoRepository.findByCodigo(code).isPresent());
        return code;
    }

    private String generarCodigoMedicamentoCorto() {
        int randomNumber = new Random().nextInt(9000) + 1000;
        return "MED" + randomNumber;
    }
}
