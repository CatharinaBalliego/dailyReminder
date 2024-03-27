package com.reminder.daily.autenticacao.application.service;

import com.reminder.daily.autenticacao.application.api.TokenResponse;
import com.reminder.daily.autenticacao.domain.Token;
import com.reminder.daily.config.security.service.TokenService;
import com.reminder.daily.credencial.application.service.CredencialService;
import com.reminder.daily.credencial.domain.Credencial;
import com.reminder.daily.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AutenticacaoApplicationService implements AutenticacaoService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final CredencialService credencialService;

    @Override
    public Token autentica(UsernamePasswordAuthenticationToken userCredentials) {
        log.info("[start] AutenticacaoApplicationService - autentica");
        var authentication = authenticationManager.authenticate(userCredentials);
        Token token = Token.builder()
                .tipo("Bearer")
                .token(tokenService.gerarToken(authentication))
                .build();
        log.info("[finish] AutenticacaoApplicationService - autentica");
        return token;
    }

    @Override
    public Token reativaToken(String tokenExpirado) {
        log.info("[start] AutenticacaoApplicationService - reativaToken");
        Credencial credencial =credencialService.buscaCredencialPorUsuario(extrairUsuario(tokenExpirado));
        log.info("[finish] AutenticacaoApplicationService - reativaToken");
        return Token.builder()
                .tipo("Bearer")
                .token(tokenService.gerarToken(credencial))
                .build();
    }

    private String extrairUsuario(String tokenExpirado){
        return tokenService.getUsuario(tokenExpirado)
                .orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Token invalido!"));
    }
}
