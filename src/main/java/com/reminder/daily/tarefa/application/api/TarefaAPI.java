package com.reminder.daily.tarefa.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/v1/tarefa")
public interface TarefaAPI {

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    TarefaIdResponse postNovaTarefa(@RequestBody @Valid TarefaRequest tarefaRequest);

    @GetMapping("/usuario/{idUsuario}")
    @ResponseStatus(code = HttpStatus.OK)
    List<TarefaResponse> getTarefasPorIdUsuario(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable UUID idUsuario);
}
