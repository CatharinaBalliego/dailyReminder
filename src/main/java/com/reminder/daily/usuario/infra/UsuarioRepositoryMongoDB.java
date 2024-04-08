package com.reminder.daily.usuario.infra;

import com.reminder.daily.handler.APIException;
import com.reminder.daily.usuario.application.repository.UsuarioRepository;
import com.reminder.daily.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UsuarioRepositoryMongoDB implements UsuarioRepository {
    private final UsuarioMongoSpringRepository usuarioMongoSpringRepository;

    @Override
    public Usuario salva(Usuario usuario) {
        log.info("[start] UsuarioRepositoryMongoDB - salva");
        Usuario novoUsuario = usuarioMongoSpringRepository.save(usuario);
        log.info("[finish] UsuarioRepositoryMongoDB - salva");
        return novoUsuario;
    }

    @Override
    public Usuario buscarUsuarioPorId(UUID usuarioId) {
        log.info("[start] UsuarioRepositoryMongoDB - buscarUsuarioPorId");
        Usuario usuario = usuarioMongoSpringRepository.findByIdUsuario(usuarioId)
                        .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Usuario não encontrado!"));
        log.info("[finish] UsuarioRepositoryMongoDB - buscarUsuarioPorId");
        return usuario;
    }

    public Usuario buscarUsuarioPorEmail(String usuarioEmail){
        log.info("[start] UsuarioRepositoryMongoDB - buscarUsuarioPorEmail");
        Usuario usuario = usuarioMongoSpringRepository.findByEmail(usuarioEmail)
                        .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Usuario não encontrado!"));
        log.info("[finish] UsuarioRepositoryMongoDB - buscarUsuarioPorEmail");
        return usuario;
    }
}
