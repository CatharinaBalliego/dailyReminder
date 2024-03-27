package com.reminder.daily.usuario.application.api;

import com.reminder.daily.usuario.application.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@RestController
public class UsuarioController implements UsuarioAPI{
    private final UsuarioService usuarioService;
    @Override
    public UsuarioNovoResponse postNovoUsuario(UsuarioNovoRequest usuarioNovoRequest) {
        log.info("[start] UsuarioController - postNovoUsuario");
        UsuarioNovoResponse usuarioNovo = usuarioService.salvarUsuario(usuarioNovoRequest);
        log.info("[finish] UsuarioController - postNovoUsuario");
        return usuarioNovo;
    }

    @Override
    public UsuarioNovoResponse buscarUsuarioPorId(UUID idUsuario) {
        log.info("[start] UsuarioController - buscarUsuarioPorId");
        UsuarioNovoResponse usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        log.info("[finish] UsuarioController - buscarUsuarioPorId");
        return usuario;
    }
}
