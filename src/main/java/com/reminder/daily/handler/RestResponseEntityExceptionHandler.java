package com.reminder.daily.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class RestResponseEntityExceptionHandler {


    public ResponseEntity<ErrorApiResponse> handlerGenericException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorApiResponse
                        .builder()
                        .description("INTERNAL SERVER ERROR!")
                        .message("INFORME AO ADMINISTRADOR DO SISTEMA!")
                        .build());

    }
}
