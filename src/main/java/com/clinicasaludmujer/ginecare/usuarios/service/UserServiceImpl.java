package com.clinicasaludmujer.ginecare.usuarios.service;

import com.clinicasaludmujer.ginecare.usuarios.exceptions.EmailAlreadyExistsException;
import com.clinicasaludmujer.ginecare.usuarios.exceptions.RoleNotFoundException;
import com.clinicasaludmujer.ginecare.usuarios.exceptions.UserAlreadyExistsException;
import com.clinicasaludmujer.ginecare.usuarios.mapper.DtoMapperUser;
import com.clinicasaludmujer.ginecare.usuarios.model.dto.UserRequest;
import com.clinicasaludmujer.ginecare.usuarios.model.dto.UserResponse;
import com.clinicasaludmujer.ginecare.usuarios.model.entity.RoleEntity;
import com.clinicasaludmujer.ginecare.usuarios.model.entity.UserEntity;
import com.clinicasaludmujer.ginecare.usuarios.repository.RoleRepository;
import com.clinicasaludmujer.ginecare.usuarios.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRespository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<UserEntity> users = repository.findAll();
        return users.stream()
                .map(DtoMapperUser::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> findById(Long id) {
        return repository.findById(id)
                .map(DtoMapperUser::map);
    }

    @Override
    @Transactional
    public UserResponse save(UserRequest userRequest) {

        if (repository.existsByUsername(userRequest.getUsername())) {
            throw new UserAlreadyExistsException("El usuario ya existe");
        }
        if (repository.existsByEmail(userRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Ese correo ya está registrado con otro usuario");
        }


        UserEntity user = new UserEntity();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        List<RoleEntity> roles = new ArrayList<>();
        if (userRequest.isAdmin()) {
            RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RoleNotFoundException("No se encontró el rol ROLE_ADMIN"));
            roles.add(adminRole);
        } else {
            RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RoleNotFoundException("No se encontró el rol ROLE_USER"));
            roles.add(userRole);
        }
        user.setRoles(roles);

        return DtoMapperUser.map(repository.save(user));
    }

    @Override
    @Transactional
    public Optional<UserResponse> update(UserRequest userRequest, Long id) {
        return repository.findById(id)
                .map(userEntity -> {
                    repository.findByUsername(userRequest.getUsername())
                            .filter(existingUser -> !existingUser.getId().equals(id))
                            .ifPresent(existingUser ->
                                    { throw new UserAlreadyExistsException("El usuario ya existe"); }
                            );
                    repository.findByEmail(userRequest.getEmail())
                            .filter(existingUser -> !existingUser.getId().equals(id))
                            .ifPresent(existingUser ->
                                    { throw new EmailAlreadyExistsException("Ese correo ya está registrado con otro usuario"); }
                            );
                    userEntity.setUsername(userRequest.getUsername());
                    userEntity.setEmail(userRequest.getEmail());

                    if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                    }

                    List<RoleEntity> roles = new ArrayList<>();
                    if (userRequest.isAdmin()) {
                        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new RoleNotFoundException("No se encontró el rol ROLE_ADMIN"));
                        roles.add(adminRole);
                    } else {
                        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                                .orElseThrow(() -> new RoleNotFoundException("No se encontró el rol ROLE_USER"));
                        roles.add(userRole);
                    }
                    userEntity.setRoles(roles);

                    return DtoMapperUser.map(repository.save(userEntity));
                });
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
