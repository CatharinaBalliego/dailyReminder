package com.reminder.daily.tarefa.application.api;

import com.reminder.daily.config.security.service.TokenService;
import com.reminder.daily.handler.APIException;
import com.reminder.daily.tarefa.application.service.TarefaService;
import com.reminder.daily.tarefa.domain.Tarefa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2
public class TarefaController implements TarefaAPI{
    private final TarefaService tarefaService;
    private final TokenService tokenService;
    @Override
    public TarefaIdResponse postNovaTarefa(TarefaRequest tarefaRequest) {
        log.info("[start] TarefaController - postNovaTarefa");
        TarefaIdResponse tarefaId = tarefaService.salvarTarefa(tarefaRequest);
        log.info("[finish] TarefaController - postNovaTarefa");
        return tarefaId;
    }

    @Override
    public List<TarefaResponse> getTarefasPorIdUsuario(String token, UUID idUsuario) {
        log.info("[start] TarefaController - getTarefasPorIdUsuario");
        String emailUsuario = getUsuarioByToken(token);
        List<TarefaResponse> tarefas = tarefaService.buscarTarefaPorIdUsuario(emailUsuario, idUsuario);
        log.info("[finish] TarefaController - getTarefasPorIdUsuario");
        return tarefas;
    }

    @Override
    public void deletarTarefaPorId(String token, UUID idUsuario, UUID idTarefa) {
        log.info("[start] TarefaController - deletarTarefaPorId");
        String emailUsuario = getUsuarioByToken(token);
        tarefaService.deletarTarefa(emailUsuario, idUsuario, idTarefa);
        log.info("[finish] TarefaController - deletarTarefaPorId");
    }

    @Override
    public void marcarTarefaConcluida(String token, UUID idTarefa) {
        log.info("[start] TarefaController - marcarTarefaConcluida");
        String emailUsuario = getUsuarioByToken(token);
        tarefaService.concluirTarefa(emailUsuario, idTarefa);
        log.info("[finish] TarefaController - marcarTarefaConcluida");
    }

    @Override
    public Tarefa getTarefa(String token, UUID idTarefa) {
        log.info("[start] TarefaController - getTarefa");
        String emailUsuario = getUsuarioByToken(token);
        tarefaService.buscarTarefaPorId(emailUsuario, idTarefa);
        log.info("[finish] TarefaController - getTarefa");
    }

    private String getUsuarioByToken(String token) {
        log.debug("[token] {}", token);
        String usuario = tokenService.getUsuarioByBearerToken(token)
                .orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, token));
        log.info("[usuario] {}", usuario);
        return usuario;
    }
}
