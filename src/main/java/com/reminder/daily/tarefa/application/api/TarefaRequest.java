package com.reminder.daily.tarefa.application.api;

import com.reminder.daily.tarefa.domain.StatusTarefa;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Value
public class TarefaRequest {

    @Size(message = "Campo descrição tarefa não pode estar vazio", max = 255, min = 3)
    @NotBlank
    private String descricao;
    @NonNull
    private UUID idUsuario;
    private StatusTarefa status;
    private UUID idProjeto;
}
