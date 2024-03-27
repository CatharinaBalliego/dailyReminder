package com.reminder.daily.autenticacao.application.api;

import com.reminder.daily.autenticacao.domain.Token;
import lombok.Value;

@Value
public class TokenResponse {
    private String token;
    private String tipo;

    public TokenResponse(Token token) {
        this.token = token.getToken();
        this.tipo = token.getTipo();
    }
}
