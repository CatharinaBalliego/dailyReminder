package com.reminder.daily.usuario.application.service;

import com.reminder.daily.credencial.application.service.CredencialService;
import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;
import com.reminder.daily.usuario.application.api.UsuarioNovoResponse;
import com.reminder.daily.usuario.application.repository.UsuarioRepository;
import com.reminder.daily.usuario.domain.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsuarioApplicationService implements UsuarioService{
    private final UsuarioRepository usuarioRepository;
    private final CredencialService credencialService;

    @Override
    public UsuarioNovoResponse salvarUsuario(@Valid UsuarioNovoRequest usuarioNovoRequest) {
        log.info("[start] UsuarioApplicationService - salvarUsuario");
        credencialService.criarNovaCredencial(usuarioNovoRequest);
        Usuario usuario = usuarioRepository.salva(new Usuario(usuarioNovoRequest));
        log.info("[finish] UsuarioApplicationService - salvarUsuario");
        return new UsuarioNovoResponse(usuario);
    }

    @Override
    public UsuarioNovoResponse buscarUsuarioPorId(UUID idUsuario) {
        log.info("[start] UsuarioApplicationService - buscarUsuarioPorId");
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(idUsuario);
        log.info("[finish] UsuarioApplicationService - buscarUsuarioPorId");
        return new UsuarioNovoResponse(usuario);
    }

    @Override
    public void validarUsuario(String emailUsuario, UUID idUsuario) {
        log.info("[start] - UsuarioApplicationService - validarUsuario");
        Usuario usuarioToken = usuarioRepository.buscarUsuarioPorEmail(emailUsuario);
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(idUsuario);
        usuario.validaUsuario(usuarioToken.getIdUsuario());
        log.info("[finish] - UsuarioApplicationService - validarUsuario");
    }


}
