package com.clinicasaludmujer.ginecare.usuarios.service;

import com.clinicasaludmujer.ginecare.usuarios.model.dto.UserRequest;
import com.clinicasaludmujer.ginecare.usuarios.model.dto.UserResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponse> findAll();
    Optional<UserResponse> findById(Long id);
    UserResponse save(UserRequest userRequest);
    Optional<UserResponse> update(UserRequest userRequest, Long id);
    void deleteById(Long id);
}