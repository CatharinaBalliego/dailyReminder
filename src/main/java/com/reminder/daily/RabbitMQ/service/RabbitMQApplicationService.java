package com.reminder.daily.RabbitMQ.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reminder.daily.RabbitMQ.application.api.EmailRequest;
import com.reminder.daily.handler.APIException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RabbitMQApplicationService implements RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;



    @SneakyThrows
    @Override
    public void validarEmailUsuario(String nomeFila, Object mensagem) {
        log.info("[start] RabbitMQApplicationService - validarEmailUsuario");
        String mensagemJson = objectMapper.writeValueAsString(mensagem);
        rabbitTemplate.convertAndSend(nomeFila, mensagemJson);
        log.info("[finish] RabbitMQApplicationService - validarEmailUsuario");

    }

    @Override
    public void alterarSenha(String nomeFila, Object mensagem) {
        log.info("[start] RabbitMQApplicationService - alterarSenha");
        log.info("[finish] RabbitMQApplicationService - alterarSenha");
    }
}
