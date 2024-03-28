package com.reminder.daily.tarefa.infra;

import com.reminder.daily.tarefa.application.repository.TarefaRepository;
import com.reminder.daily.tarefa.domain.Tarefa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Log4j2
public class TarefaInfraRepository implements TarefaRepository {
    private final TarefaSpringMongoDBRepository tarefaSpringMongoDBRepository;
    @Override
    public Tarefa salva(Tarefa tarefa) {
        log.info("[start] TarefaInfraRepository - salva");
        Tarefa novaTarefa = tarefaSpringMongoDBRepository.save(tarefa);
        log.info("[finish] TarefaInfraRepository - salva");
        return novaTarefa;
    }

    @Override
    public List<Tarefa> buscarTodasTarefasDoUsuario(UUID idUsuario) {
        log.info("[start] TarefaInfraRepository - buscarTodasTarefasDoUsuario");
        List<Tarefa> tarefas = tarefaSpringMongoDBRepository.findAllByIdUsuario(idUsuario);
        log.info("[finish] TarefaInfraRepository - buscarTodasTarefasDoUsuario");
        return tarefas;
    }

}
