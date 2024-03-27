package com.reminder.daily.credencial.application.service;

import com.reminder.daily.credencial.application.repository.CredencialRepository;
import com.reminder.daily.credencial.domain.Credencial;
import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Log4j2
public class CredencialApplicationService implements CredencialService {
    private final CredencialRepository credencialRepository;
    @Override
    public void criarNovaCredencial(@Valid UsuarioNovoRequest usuario) {
        log.info("[start] CredencialApplicationService - criarNovaCredencial");
        Credencial novaCredencial = new Credencial(usuario.getEmail(), usuario.getSenha());
        credencialRepository.salva(novaCredencial);
        log.info("[finish] CredencialApplicationService - criarNovaCredencial");


    }

    @Override
    public Credencial buscaCredencialPorUsuario(String usuario) {
        log.info("[start] CredencialApplicationService - buscaCredencialPorUsuario");
        Credencial credencial = credencialRepository.buscaCredencialPorUsuario(usuario);
        log.info("[finish] CredencialApplicationService - buscaCredencialPorUsuario");
        return credencial;
    }
}
