package com.clinicasaludmujer.ginecare.usuarios.mapper;

import com.clinicasaludmujer.ginecare.usuarios.model.dto.UserResponse;
import com.clinicasaludmujer.ginecare.usuarios.model.entity.UserEntity;

public class DtoMapperUser {

    public static UserResponse map(UserEntity user) {
        if (user == null) {
            throw new RuntimeException("Debe enviarse una entidad usuario válida.");
        }

        // Se determina si el usuario es admin revisando si tiene el rol "ROLE_ADMIN"
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                isAdmin
        );
    }
}
