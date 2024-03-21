package com.reminder.daily.config.security.service;

import com.reminder.daily.credencial.application.repository.CredencialRepository;
import com.reminder.daily.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutenticacaoSecurityService implements UserDetailsService {
    private final CredencialRepository credencialRepository;
    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        log.info("[inicio] AutenticacaoSecurityService - buscando credencial pelo usuario");
        var credencial = credencialRepository.buscaCredencialPorUsuario(usuario);
        log.info("[finaliza] AutenticacaoSecurityService - buscando credencial pelo usuario");
        return Optional.ofNullable(credencial).orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Não existe credencial para o Usuario informado!"));
    }
}
