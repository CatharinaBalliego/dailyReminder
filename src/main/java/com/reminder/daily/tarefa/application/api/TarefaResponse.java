package com.reminder.daily.tarefa.application.api;

import com.reminder.daily.tarefa.domain.StatusTarefa;
import com.reminder.daily.tarefa.domain.Tarefa;
import lombok.Value;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
public class TarefaResponse {
    private UUID idTarefa;
    private String descricao;
    private UUID idUsuario;
    private StatusTarefa status;
    private UUID idProjeto;

    public TarefaResponse(Tarefa tarefa) {
        this.idTarefa = tarefa.getIdTarefa();
        this.descricao = tarefa.getDescricao();
        this.idUsuario = tarefa.getIdUsuario();
        this.status = tarefa.getStatus();
        this.idProjeto = tarefa.getIdProjeto();
    }

    public static List<TarefaResponse> converte(List<Tarefa> tarefas){
        return tarefas.stream()
                .map(TarefaResponse::new)
                .collect(Collectors.toList());
    }
}
