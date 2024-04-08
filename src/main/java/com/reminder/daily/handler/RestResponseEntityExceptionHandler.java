package com.reminder.daily.handler;

import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorApiResponse> handlerGenericException(APIException ex) {
        return ex.buildErrorResponseEntity();
    }

    @ExceptionHandler(Exception.class)
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

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<ErrorApiResponse> handlerMongoException(Exception error){
        log.error("Mongo exception: ", error);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorApiResponse
                        .builder()
                        .description("CONFLICT!")
                        .message("EMAIL JA ESTA CADASTRADO NO SISTEMA!")
                        .build());
    }
}
