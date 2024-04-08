package com.reminder.daily.tarefa.infra;

import com.reminder.daily.tarefa.domain.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface TarefaSpringMongoDBRepository extends MongoRepository<Tarefa, UUID> {
    List<Tarefa> findAllByIdUsuario(UUID idUsuario);

}
