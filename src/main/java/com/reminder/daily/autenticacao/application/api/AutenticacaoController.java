package com.reminder.daily.autenticacao.application.api;

import com.reminder.daily.autenticacao.application.service.AutenticacaoService;
import com.reminder.daily.config.security.domain.ValidaConteudoAuthorizationHeader;
import com.reminder.daily.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AutenticacaoController implements AutenticacaoAPI {
    private final AutenticacaoService autenticacaoService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public TokenResponse autetica(AutenticacaoRequest autenticacaoRequest){
        log.info("[start] AutenticacaoController - autetica");
        var token = autenticacaoService.autentica(autenticacaoRequest.getUserPassToken());
        log.info("[finish] AutenticacaoController - autetica");
        return new TokenResponse(token);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public TokenResponse reativaAuteticacao(String tokenExpirado) throws AuthenticationException {
        log.info("[start] AutenticacaoController - reativaAuteticacao");
        String tokenValidado = validaTokenExpirado(Optional.of(tokenExpirado));
        var token = autenticacaoService.reativaAutenticacao(tokenValidado);
        log.info("[finish] AutenticacaoController - reativaAuteticacao");
        return new TokenResponse(token);
    }

    private String validaTokenExpirado(Optional<String> tokenExpirado) {
        String tokenExp = tokenExpirado.filter(new ValidaConteudoAuthorizationHeader())
                .orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Token Invalido!"));
        return tokenExp.substring(7, tokenExp.length());
    }
}
