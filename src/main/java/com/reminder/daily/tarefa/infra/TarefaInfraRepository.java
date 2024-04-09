package com.reminder.daily.tarefa.infra;

import com.reminder.daily.handler.APIException;
import com.reminder.daily.tarefa.application.api.TarefaResponse;
import com.reminder.daily.tarefa.application.repository.TarefaRepository;
import com.reminder.daily.tarefa.domain.Tarefa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public void deletarTarefa(UUID idTarefa) {
        log.info("[start] TarefaInfraRepository - deletarTarefa");
        tarefaSpringMongoDBRepository.deleteById(idTarefa);
        log.info("[finish] TarefaInfraRepository - deletarTarefa");
    }

    @Override
    public void concluirTarefa(UUID idTarefa) {
        log.info("[start] TarefaInfraRepository - concluirTarefa");
        Tarefa tarefa = tarefaSpringMongoDBRepository.findById(idTarefa)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));
        tarefa.marcarTarefaConcluida();
        tarefaSpringMongoDBRepository.save(tarefa);
        log.info("[finish] TarefaInfraRepository - concluirTarefa");
    }

    @Override
    public Optional<Tarefa> buscarTarefaPorId(UUID idTarefa) {
        log.info("[start] TarefaInfraRepository - buscarTarefaPorId");
        Optional<Tarefa> tarefa = tarefaSpringMongoDBRepository.findById(idTarefa);
        log.info("[finish] TarefaInfraRepository - buscarTarefaPorId");
        return tarefa;
    }

    @Override
    public void resetarTodasTarefas() {
        log.info("[start] TarefaInfraRepository - resetarTodasTarefas");
        List<Tarefa> tarefas = tarefaSpringMongoDBRepository.findAll();
        tarefas.stream().forEach(Tarefa::resetar);
        tarefaSpringMongoDBRepository.saveAll(tarefas);
        log.info("[finish] TarefaInfraRepository - resetarTodasTarefas");
    }

}
