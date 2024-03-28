package com.reminder.daily.tarefa.application.service;

import com.reminder.daily.tarefa.application.api.TarefaIdResponse;
import com.reminder.daily.tarefa.application.api.TarefaRequest;
import com.reminder.daily.tarefa.application.api.TarefaResponse;
import com.reminder.daily.tarefa.application.repository.TarefaRepository;
import com.reminder.daily.tarefa.domain.Tarefa;
import com.reminder.daily.usuario.application.repository.UsuarioRepository;
import com.reminder.daily.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    @Override
    public TarefaIdResponse salvarTarefa(@Valid TarefaRequest tarefaRequest) {
        log.info("[start] TarefaApplicationService - salvaTarefa");
        Tarefa tarefa = tarefaRepository.salva(new Tarefa(tarefaRequest));
        log.info("[finish] TarefaApplicationService - salvaTarefa");
        return TarefaIdResponse.builder().idTarefa(tarefa.getIdTarefa()).build();
    }

    @Override
    public List<TarefaResponse> buscarTarefaPorIdUsuario(String emailUsuario, UUID idUsuario) {
        log.info("[start] TarefaApplicationService - buscarTarefaPorIdUsuario");
        Usuario usuarioToken = usuarioRepository.buscarUsuarioPorEmail(emailUsuario);
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(idUsuario);
        usuario.validaUsuario(usuarioToken.getIdUsuario());
        List<Tarefa> tarefas = tarefaRepository.buscarTodasTarefasDoUsuario(idUsuario);
        log.info("[finish] TarefaApplicationService - buscarTarefaPorIdUsuario");
        return TarefaResponse.converte(tarefas);
    }
}
