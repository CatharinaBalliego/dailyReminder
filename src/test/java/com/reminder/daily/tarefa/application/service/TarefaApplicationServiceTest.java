package com.reminder.daily.tarefa.application.service;

import com.reminder.daily.DataHelper;
import com.reminder.daily.handler.APIException;
import com.reminder.daily.tarefa.application.api.TarefaIdResponse;
import com.reminder.daily.tarefa.application.api.TarefaRequest;
import com.reminder.daily.tarefa.application.api.TarefaResponse;
import com.reminder.daily.tarefa.application.repository.TarefaRepository;
import com.reminder.daily.tarefa.domain.StatusTarefa;
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
    UsuarioService usuarioService;
    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    public void salvarTarefa_valida_deveRetornarId(){
        Tarefa tarefa = DataHelper.criarTarefaAFazer();
        TarefaRequest tarefaRequest = DataHelper.getTarefaRequest();
        Tarefa tarefaResponse = DataHelper.criarTarefaAFazer();
        String email = "teste@teste.com";

        when(tarefaRepository.salva(any(Tarefa.class))).thenReturn(tarefaResponse);
        tarefaApplicationService.salvarTarefa(email, tarefaRequest);
        verify(usuarioService, times(1)).validarUsuario(email, tarefaRequest.getIdUsuario());
        assertEquals(tarefaResponse.getIdTarefa(), tarefa.getIdTarefa());
        assertEquals(tarefaResponse.getStatus(), StatusTarefa.A_FAZER);
    }

    @Test
    public void salvarTarefa_tokenInvalido_deveRetornarErro() {
        Tarefa tarefa = DataHelper.criarTarefaAFazer();
        TarefaRequest tarefaRequest = DataHelper.getTarefaRequest();
        String email = "teste@teste.com";

        doThrow(APIException.build(HttpStatus.UNAUTHORIZED,"Credencial de autenticação não é valida!"))
                .when(usuarioService).validarUsuario(email, tarefaRequest.getIdUsuario());

        APIException exception = Assertions.assertThrows(APIException.class,
                () -> tarefaApplicationService.salvarTarefa(email, tarefaRequest));

        verify(tarefaRepository, never()).salva(tarefa);
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusException());
        assertEquals("Credencial de autenticação não é valida!", exception.getMessage());
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
        String email = "teste@teste.com";
        UUID idTarefaInvalido = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();

        doThrow(APIException.build(HttpStatus.UNAUTHORIZED, "Credencial de autenticação não é valida!"))
                .when(usuarioService).validarUsuario(email, idUsuario);

        APIException exception = Assertions.assertThrows(APIException.class,
                () -> tarefaApplicationService.deletarTarefa(email, idUsuario, idTarefaInvalido));

        verify(usuarioService, times(1)).validarUsuario(email, idUsuario);
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusException());
        assertEquals("Credencial de autenticação não é valida!", exception.getMessage());
        verify(tarefaRepository, never()).deletarTarefa(idTarefaInvalido);
    }

    @Test
    public void deletarTarefa_tokenValido_OK(){
        Usuario usuarioToken = DataHelper.getUsuario();
        Tarefa tarefa = DataHelper.criarTarefaAFazer();

        when(usuarioRepository.buscarUsuarioPorEmail(usuarioToken.getEmail())).thenReturn(usuarioToken);
        when(tarefaRepository.buscarTarefaPorId((tarefa.getIdTarefa()))).thenReturn(Optional.of(tarefa));

        tarefa.validarUsuario(usuarioToken.getIdUsuario());
        tarefaApplicationService.deletarTarefa(usuarioToken.getEmail(), usuarioToken.getIdUsuario(), tarefa.getIdTarefa());

        verify(usuarioService, times(1)).validarUsuario(any(String.class), any(UUID.class));
        verify(tarefaRepository, times(1)).deletarTarefa(any(UUID.class));
    }

    @Test
    public void concluirTarefa_idTarefaInvalido_NotFound() {
        String email = "teste@teste.com";
        Usuario usuariotoken = DataHelper.getUsuario();
        Tarefa tarefa = DataHelper.criarTarefaAFazer();

        when(usuarioRepository.buscarUsuarioPorEmail(email)).thenReturn(usuariotoken);

        doThrow(APIException.build(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"))
                .when(tarefaRepository).buscarTarefaPorId(tarefa.getIdTarefa());

        APIException exception = Assertions.assertThrows(APIException.class,
                () -> tarefaApplicationService.concluirTarefa(email, tarefa.getIdTarefa()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
        assertEquals("Tarefa não encontrada!", exception.getMessage());

    }
    @Test
    public void concluirTarefa_idTarefaValido_OK() {
        String email = "teste@teste.com";
        Usuario usuariotoken = DataHelper.getUsuario();
        Tarefa tarefa = DataHelper.criarTarefaAFazer();

        when(usuarioRepository.buscarUsuarioPorEmail(email)).thenReturn(usuariotoken);
        when(tarefaRepository.buscarTarefaPorId(tarefa.getIdTarefa())).thenReturn(Optional.of(tarefa));
        tarefaApplicationService.concluirTarefa(email, tarefa.getIdTarefa());
        tarefa.marcarTarefaConcluida();

        verify(tarefaRepository, times(1)).salva(tarefa);
        assertEquals(StatusTarefa.CONCLUIDA, tarefa.getStatus());
    }
    @Test
    public void buscarTarefaPorId_idTarefaInvalida_NotFound() {
        String email = "teste@teste.com";
        Usuario usuarioToken = DataHelper.getUsuario();
        Tarefa tarefa = DataHelper.criarTarefaAFazer();

        when(usuarioRepository.buscarUsuarioPorEmail(email)).thenReturn(usuarioToken);

        doThrow(APIException.build(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"))
                .when(tarefaRepository).buscarTarefaPorId(tarefa.getIdTarefa());

        APIException exception = Assertions.assertThrows(APIException.class,
                () -> tarefaApplicationService.buscarTarefaPorId(email, tarefa.getIdTarefa()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
        assertEquals("Tarefa não encontrada!", exception.getMessage());
    }
    @Test
    public void buscarTarefaPorId_idTarefaValido_retornarTarefa() {
        String email = "teste@teste.com";
        Usuario usuarioToken = DataHelper.getUsuario();
        Tarefa tarefa = DataHelper.criarTarefaAFazer();

        when(usuarioRepository.buscarUsuarioPorEmail(email)).thenReturn(usuarioToken);
        when(tarefaRepository.buscarTarefaPorId(tarefa.getIdTarefa())).thenReturn(Optional.of(tarefa));

        tarefaApplicationService.buscarTarefaPorId(usuarioToken.getEmail(), tarefa.getIdTarefa());
        tarefa.validarUsuario(usuarioToken.getIdUsuario());

        assertEquals(usuarioToken.getIdUsuario(), tarefa.getIdUsuario());

    }
    @Test
    public void resetarTarefa_idTarefaInvalido_NotFound() {
        String email = "teste@teste.com";
        Usuario usuariotoken = DataHelper.getUsuario();
        Tarefa tarefa = DataHelper.criarTarefaAFazer();

        when(usuarioRepository.buscarUsuarioPorEmail(email)).thenReturn(usuariotoken);

        doThrow(APIException.build(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"))
                .when(tarefaRepository).buscarTarefaPorId(tarefa.getIdTarefa());

        APIException exception = Assertions.assertThrows(APIException.class,
                () -> tarefaApplicationService.resetarTarefa(email, tarefa.getIdTarefa()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
        assertEquals("Tarefa não encontrada!", exception.getMessage());

    }
    @Test
    public void resetarTarefa_tokenValido_OK () {
        String email = "teste@teste.com";
        Usuario usuariotoken = DataHelper.getUsuario();
        Tarefa tarefa = DataHelper.criarTarefaAFazer();

        when(usuarioRepository.buscarUsuarioPorEmail(email)).thenReturn(usuariotoken);
        when(tarefaRepository.buscarTarefaPorId(tarefa.getIdTarefa())).thenReturn(Optional.of(tarefa));
        tarefaApplicationService.resetarTarefa(email, tarefa.getIdTarefa());
        tarefa.resetar();

        verify(tarefaRepository, times(1)).salva(tarefa);
        assertEquals(StatusTarefa.A_FAZER, tarefa.getStatus());

    }
}
