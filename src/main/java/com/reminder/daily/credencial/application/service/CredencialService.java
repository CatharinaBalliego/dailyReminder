package com.reminder.daily.credencial.application.service;

import com.reminder.daily.credencial.domain.Credencial;
import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;
import jakarta.validation.Valid;

public interface CredencialService {
    void criarNovaCredencial(@Valid UsuarioNovoRequest usuarioNovoRequest);
    Credencial buscaCredencialPorUsuario(String usuario);
}
