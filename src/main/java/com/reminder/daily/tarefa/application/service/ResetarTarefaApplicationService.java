package com.reminder.daily.tarefa.application.service;

import com.reminder.daily.tarefa.application.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Log4j2
@RequiredArgsConstructor
public class ResetarTarefaApplicationService {
    private final TarefaRepository tarefaRepository;
    @Scheduled(cron = "0 0 21 * * *") // reseta diariamente as 21h00
    public void resetarTarefaDiaria() {
        log.info("[start] ResetarTarefaApplicationService - resetarTarefaDiaria");
        tarefaRepository.resetarTodasTarefas();
        log.info("Tarefas resetadas!");
        log.info("[finish] ResetarTarefaApplicationService - resetarTarefaDiaria");
    }

}
