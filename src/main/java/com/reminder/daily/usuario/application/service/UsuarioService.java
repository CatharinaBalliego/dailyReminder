package com.reminder.daily.usuario.application.service;

import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;
import com.reminder.daily.usuario.application.api.UsuarioNovoResponse;

import java.util.UUID;

public interface UsuarioService {
    UsuarioNovoResponse salvarUsuario(UsuarioNovoRequest usuario);
    UsuarioNovoResponse buscarUsuarioPorId(UUID idUsuario);
}
