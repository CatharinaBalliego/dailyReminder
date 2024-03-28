package com.reminder.daily.tarefa.application.service;

import com.reminder.daily.tarefa.application.api.TarefaIdResponse;
import com.reminder.daily.tarefa.application.api.TarefaRequest;
import com.reminder.daily.tarefa.application.api.TarefaResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface TarefaService {
    TarefaIdResponse salvarTarefa(@Valid TarefaRequest tarefaRequest);
    List<TarefaResponse> buscarTarefaPorIdUsuario(String emailUsuario, UUID idUsuario);

}
