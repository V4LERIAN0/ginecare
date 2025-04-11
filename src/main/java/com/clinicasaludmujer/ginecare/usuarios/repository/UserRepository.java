package com.clinicasaludmujer.ginecare.usuarios.repository;

import org.springframework.security.core.userdetails.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    User save(User user);

    void deleteById(Long id);
}
