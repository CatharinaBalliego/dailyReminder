package com.reminder.daily.usuario.application.repository;

import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;
import com.reminder.daily.usuario.domain.Usuario;

import java.util.UUID;

public interface UsuarioRepository {
    Usuario salva(Usuario usuario);
    Usuario buscarUsuarioPorId(UUID usuarioId);
}
