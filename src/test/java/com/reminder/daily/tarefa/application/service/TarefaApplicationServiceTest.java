package com.reminder.daily.tarefa.application.service;

import com.reminder.daily.DataHelper;
import com.reminder.daily.handler.APIException;
import com.reminder.daily.tarefa.application.api.TarefaIdResponse;
import com.reminder.daily.tarefa.application.api.TarefaRequest;
import com.reminder.daily.tarefa.application.api.TarefaResponse;
import com.reminder.daily.tarefa.application.repository.TarefaRepository;
import com.reminder.daily.tarefa.domain.Tarefa;
import com.reminder.daily.usuario.application.repository.UsuarioRepository;
import com.reminder.daily.usuario.application.service.UsuarioService;
import com.reminder.daily.usuario.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TarefaApplicationServiceTest {

    @InjectMocks
    TarefaApplicationService tarefaApplicationService;
    @Mock
    TarefaRepository tarefaRepository;
    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    UsuarioService usuarioService;

    @Test
    public void salvarTarefa_valida_deveRetornarId(){
        Tarefa tarefa = DataHelper.criarTarefaAFazer();
        TarefaRequest tarefaRequest = DataHelper.getTarefaRequest();
        Tarefa tarefaResponse = DataHelper.criarTarefaAFazer();

        //when(tarefaRepository.salva(tarefa)).thenReturn(tarefaResponse);
        tarefaApplicationService.salvarTarefa(tarefaRequest);
       // verify(usuarioService, times(1)).validarUsuario();
        assertEquals(tarefaResponse.getIdTarefa(), tarefa.getIdTarefa());
    }

    @Test
    public void salvarTarefa_tokenInvalido_deveRetornarErro() {

    }

    @Test
    public void buscarTarefaPorIdUsuario_tokenInvalido_Unauthorized(){
        UUID idUsuario = UUID.randomUUID();
        String email = "teste@teste.com";

        doThrow(APIException.build(HttpStatus.UNAUTHORIZED,"Credencial de autenticação nao é valida!"))
                .when(usuarioService).validarUsuario(email, idUsuario);

        APIException exception = Assertions.assertThrows(APIException.class,
                () -> tarefaApplicationService.buscarTarefaPorIdUsuario(email, idUsuario));

        assertEquals("Credencial de autenticação nao é valida!", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusException());
    }

    @Test
    public void buscarTarefaPorIdUsuario_tokenValido_deveRetornarTarefa(){
        UUID idUsuario = UUID.randomUUID();
        String email = "teste@teste.com";
        List<Tarefa> tarefasDoUsuario = DataHelper.getTarefasDoUsuario(idUsuario);

        //usuarioService.validarUsuario(email, idUsuario);
        when(tarefaRepository.buscarTodasTarefasDoUsuario(idUsuario)).thenReturn(tarefasDoUsuario);

        List<TarefaResponse> tarefaResponseList = tarefaApplicationService.buscarTarefaPorIdUsuario(email, idUsuario);

        assertEquals(3, tarefaResponseList.size());
        verify(usuarioService, times(1)).validarUsuario(email, idUsuario);

    }

    @Test
    public void deletarTarefa_tokenInvalido_Unauthorized(){
    }

    @Test
    public void deletarTarefa_tokenValido_OK(){

    }

    @Test
    public void concluirTarefa_tokenInvalido_Unauthorized() {

    }
    @Test
    public void concluirTarefa_tokenValido_OK() {

    }
    @Test
    public void buscarTarefaPorId_idtarefaInvalida_NotFound() {

    }
    @Test
    public void buscarTarefaPorId_idTarefaValido_retornarTarefa() {

    }
    @Test
    public void resetarTarefa_tokenInvalido_Unauthorized() {

    }
    @Test
    public void resetarTarefa_tokenValido_OK () {

    }
}
