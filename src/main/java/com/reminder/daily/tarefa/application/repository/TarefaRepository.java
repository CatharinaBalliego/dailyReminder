package com.reminder.daily.tarefa.application.repository;

import com.reminder.daily.tarefa.domain.Tarefa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarefaRepository {
    Tarefa salva(Tarefa tarefa);
    List<Tarefa> buscarTodasTarefasDoUsuario(UUID idUsuario);
    void deletarTarefa(UUID idTarefa);
    void concluirTarefa(UUID idTarefa);
    Optional<Tarefa> buscarTarefaPorId(UUID idTarefa);
    void resetarTodasTarefas();
}
