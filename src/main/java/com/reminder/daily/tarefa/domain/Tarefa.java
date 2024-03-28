package com.reminder.daily.tarefa.domain;

import com.reminder.daily.tarefa.application.api.TarefaRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Document(collection = "Tarefa")
public class Tarefa {
    @Id
    private UUID idTarefa;
    @NotBlank
    private String descricao;
    @Indexed
    private UUID idUsuario;
    private StatusTarefa status;
    @Indexed
    private UUID idProjeto;

    public Tarefa(TarefaRequest tarefaRequest) {
        this.idTarefa = UUID.randomUUID();
        this.descricao = tarefaRequest.getDescricao();
        this.idUsuario = tarefaRequest.getIdUsuario();
        this.status = tarefaRequest.getStatus();
        this.idProjeto = tarefaRequest.getIdProjeto();
    }


}
