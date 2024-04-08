package com.reminder.daily.tarefa.application.service;

import com.reminder.daily.handler.APIException;
import com.reminder.daily.tarefa.application.api.TarefaIdResponse;
import com.reminder.daily.tarefa.application.api.TarefaRequest;
import com.reminder.daily.tarefa.application.api.TarefaResponse;
import com.reminder.daily.tarefa.application.repository.TarefaRepository;
import com.reminder.daily.tarefa.domain.Tarefa;
import com.reminder.daily.usuario.application.repository.UsuarioRepository;
import com.reminder.daily.usuario.application.service.UsuarioService;
import com.reminder.daily.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class TarefaApplicationService implements TarefaService{
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    @Override
    public TarefaIdResponse salvarTarefa(String emailUsuario, @Valid TarefaRequest tarefaRequest) {
        log.info("[start] TarefaApplicationService - salvaTarefa");
        usuarioService.validarUsuario(emailUsuario, tarefaRequest.getIdUsuario());
        Tarefa tarefa = tarefaRepository.salva(new Tarefa(tarefaRequest));
        log.info("[finish] TarefaApplicationService - salvaTarefa");
        return TarefaIdResponse.builder().idTarefa(tarefa.getIdTarefa()).build();
    }

    @Override
    public List<TarefaResponse> buscarTarefaPorIdUsuario(String emailUsuario, UUID idUsuario) {
        log.info("[start] TarefaApplicationService - buscarTarefaPorIdUsuario");
        usuarioService.validarUsuario(emailUsuario, idUsuario);
        List<Tarefa> tarefas = tarefaRepository.buscarTodasTarefasDoUsuario(idUsuario);
        log.info("[finish] TarefaApplicationService - buscarTarefaPorIdUsuario");
        return TarefaResponse.converte(tarefas);
    }

    @Override
    public void deletarTarefa(String emailUsuario, UUID idUsuario, UUID idTarefa) {
        log.info("[start] TarefaApplicationService - deletarTarefa");
        usuarioService.validarUsuario(emailUsuario, idUsuario);
        buscarTarefaPorId(emailUsuario, idTarefa);
        tarefaRepository.deletarTarefa(idTarefa);
        log.info("[finish] TarefaApplicationService - deletarTarefa");

    }

    @Override
    public void concluirTarefa(String emailUsuario, UUID idTarefa) {
        log.info("[start] TarefaApplicationService - concluirTarefa");
        Tarefa tarefa = buscarTarefaPorId(emailUsuario, idTarefa);
        tarefa.marcarTarefaConcluida();
        tarefaRepository.salva(tarefa);
        log.info("[finish] TarefaApplicationService - concluirTarefa");
    }

    @Override
    public Tarefa buscarTarefaPorId(String emailUsuario, UUID idTarefa) {
        log.info("[start] TarefaApplicationService - buscarTarefaPorId");
        Usuario usuarioToken = usuarioRepository.buscarUsuarioPorEmail(emailUsuario);
        Tarefa tarefa = tarefaRepository.buscarTarefaPorId(idTarefa)
                        .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));
        tarefa.validarUsuario(usuarioToken.getIdUsuario());
        log.info("[finish] TarefaApplicationService - buscarTarefaPorId");
        return tarefa;
    }

    @Override
    public void resetarTarefa(String emailUsuario, UUID idTarefa) {
        log.info("[start] TarefaApplicationService - concluirTarefa");
        Tarefa tarefa = buscarTarefaPorId(emailUsuario, idTarefa);
        tarefa.resetar();
        tarefaRepository.salva(tarefa);
        log.info("[finish] TarefaApplicationService - concluirTarefa");
    }
}
