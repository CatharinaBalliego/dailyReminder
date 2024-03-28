package com.reminder.daily.tarefa.application.api;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class TarefaIdResponse {
    private UUID idTarefa;
}
