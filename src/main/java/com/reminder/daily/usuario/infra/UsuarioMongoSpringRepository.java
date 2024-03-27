package com.reminder.daily.usuario.infra;

import com.reminder.daily.usuario.domain.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioMongoSpringRepository extends MongoRepository<Usuario, UUID> {
    Optional<Usuario> findByIdUsuario(UUID usuarioId);

}
