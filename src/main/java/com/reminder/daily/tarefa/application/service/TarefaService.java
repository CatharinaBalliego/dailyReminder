package com.reminder.daily.tarefa.application.service;

import com.reminder.daily.tarefa.application.api.TarefaIdResponse;
import com.reminder.daily.tarefa.application.api.TarefaRequest;
import com.reminder.daily.tarefa.application.api.TarefaResponse;
import com.reminder.daily.tarefa.domain.Tarefa;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface TarefaService {
    TarefaIdResponse salvarTarefa(String email, @Valid TarefaRequest tarefaRequest);
    List<TarefaResponse> buscarTarefaPorIdUsuario(String emailUsuario, UUID idUsuario);
    void deletarTarefa(String email, UUID idUsuario, UUID idTarefa);
    void concluirTarefa(String emailUsuario, UUID idTarefa);
    Tarefa buscarTarefaPorId(String emailUsuario, UUID idTarefa);
    void resetarTarefa(String token, UUID idTarefa);

}
