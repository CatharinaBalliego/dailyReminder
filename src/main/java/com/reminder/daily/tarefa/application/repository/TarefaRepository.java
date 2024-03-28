package com.reminder.daily.tarefa.application.repository;

import com.reminder.daily.tarefa.domain.Tarefa;

import java.util.List;
import java.util.UUID;

public interface TarefaRepository {
    Tarefa salva(Tarefa tarefa);
    List<Tarefa> buscarTodasTarefasDoUsuario(UUID idUsuario);
}
