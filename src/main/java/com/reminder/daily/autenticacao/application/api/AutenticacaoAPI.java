package com.reminder.daily.autenticacao.application.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RequestMapping("/public/v1/autenticacao")
public interface AutenticacaoAPI {

    @PostMapping
    TokenResponse autentica(@RequestBody @Valid AutenticacaoRequest autenticacaoRequest) throws AuthenticationException;
    @PostMapping("/reativacao")
    TokenResponse reativaAuteticacao(@RequestHeader("Authorization") String tokenExpirado) throws AuthenticationException;

}
