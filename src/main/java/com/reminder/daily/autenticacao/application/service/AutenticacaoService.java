package com.reminder.daily.autenticacao.application.service;

import com.reminder.daily.autenticacao.application.api.TokenResponse;
import com.reminder.daily.autenticacao.domain.Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AutenticacaoService {
    Token autentica(UsernamePasswordAuthenticationToken userCredentials);

    Token reativaToken(String tokenExpirado);
}
