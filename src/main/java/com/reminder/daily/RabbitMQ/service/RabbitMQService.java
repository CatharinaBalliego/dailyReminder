package com.reminder.daily.RabbitMQ.service;

import org.springframework.stereotype.Service;

@Service
public interface RabbitMQService {

    void validarEmailUsuario(String nomeFila, Object mensagem);
    void alterarSenha(String nomeFila, Object mensagem);

}
