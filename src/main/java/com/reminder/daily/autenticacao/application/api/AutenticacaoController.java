package com.reminder.daily.autenticacao.application.api;

import com.reminder.daily.autenticacao.application.service.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AutenticacaoController implements AutenticacaoAPI {
    private final AutenticacaoService autenticacaoService;

    @Override
    public TokenResponse autetica(AutenticacaoRequest autenticacaoRequest){
        log.info("[start] AutenticacaoController - autetica");
        var token = autenticacaoService.autentica(autenticacaoRequest.getUserPassToken());
        log.info("[finish] AutenticacaoController - autetica");
        return new TokenResponse(token);
    }

    @Override
    public TokenResponse reativaAuteticacao(String tokenExpirado){
        log.info("[start] AutenticacaoController - reativaAuteticacao");
        log.info("[finish] AutenticacaoController - reativaAuteticacao");
        return null;
    }
}
