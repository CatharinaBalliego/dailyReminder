package com.reminder.daily.usuario.application.service;

import com.reminder.daily.DataHelper;
import com.reminder.daily.credencial.application.service.CredencialService;
import com.reminder.daily.handler.APIException;
import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;
import com.reminder.daily.usuario.application.api.UsuarioNovoResponse;
import com.reminder.daily.usuario.application.repository.UsuarioRepository;
import com.reminder.daily.usuario.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


//

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UsuarioApplicationServiceTest {
    @InjectMocks
    UsuarioApplicationService usuarioApplicationService;
    @Mock
    CredencialService credencialService;
    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuario_valido_deveRetornarId(){
        UsuarioNovoRequest usuarioRequest = DataHelper.getUsuarioRequest();
        Usuario usuario = new Usuario(usuarioRequest);


        when(usuarioRepository.salva(any(Usuario.class))).thenReturn(usuario);
        UsuarioNovoResponse usuarioResponse = usuarioApplicationService.salvarUsuario(usuarioRequest);

        assertNotNull(usuarioResponse);
        assertEquals(UsuarioNovoResponse.class, usuarioResponse.getClass());
        assertEquals(UUID.class, usuarioResponse.getIdUsuario().getClass());
        verify(credencialService, times(1)).criarNovaCredencial(usuarioRequest);
        //verify(usuarioRepository, times(1)).salva(new Usuario(usuarioRequest));
    }

    @Test
    public void salvarUsuario_emailDuplicado_Conflict(){
        Usuario usuario = DataHelper.getUsuario();
        UsuarioNovoRequest usuarioNovoRequest = DataHelper.getUsuarioRequest();
        when(usuarioRepository.salva(any(Usuario.class)))
                .thenThrow(APIException.build(HttpStatus.CONFLICT, "Usuario já está cadastrado no sistema!"));
        APIException exception = Assertions.assertThrows(APIException.class,
                () -> usuarioApplicationService.salvarUsuario(usuarioNovoRequest));
        assertEquals("Usuario já está cadastrado no sistema!",exception.getMessage());

    }

    @Test
    public void buscarUsuarioPorId_valido_UsuarioResponse(){
        UUID usuarioId = UUID.fromString("a713162f-20a9-5db9-a85b-90cd52ab18f4");
        Usuario usuario = DataHelper.getUsuario();
        UsuarioNovoResponse usuarioResponse = DataHelper.getUsuarioResponse();

        Mockito.lenient().when(usuarioRepository.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);


        assertEquals(usuario.getIdUsuario(), usuarioResponse.getIdUsuario());
    }


    @Test
    public void buscarUsuarioPorId_idInvalido_NotFound(){
        UUID usuarioId = UUID.randomUUID();

        when(usuarioRepository.buscarUsuarioPorId(any(UUID.class)))
                .thenThrow(APIException.build(HttpStatus.NOT_FOUND,"Usuario não encontrado!"));

        APIException exception = Assertions.assertThrows(APIException.class,
                () -> usuarioApplicationService.buscarUsuarioPorId(usuarioId));

        assertEquals("Usuario não encontrado!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
    }


    @Test
    public void validarUsuario_tokenValido_Unauthorized(){
        UUID usuarioId = UUID.randomUUID();
        String emailUsuario = "teste@teste.com";
        Usuario usuarioToken = DataHelper.getUsuario();
        Usuario usuario = DataHelper.getUsuario();

        when(usuarioRepository.buscarUsuarioPorEmail(emailUsuario)).thenReturn(usuarioToken);
        when(usuarioRepository.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);

        usuarioApplicationService.validarUsuario(emailUsuario, usuarioId);
        usuario.validaUsuario(usuarioToken.getIdUsuario());

        assertEquals(usuarioToken.getIdUsuario(), usuario.getIdUsuario());
    }

    @Test
    public void validarUsuario_tokeInValido_Unauthorized(){
        UUID usuarioId = UUID.randomUUID();
        String emailUsuario = "teste@teste.com";

        when(usuarioRepository.buscarUsuarioPorEmail(any(String.class)))
                .thenThrow(APIException.build(HttpStatus.UNAUTHORIZED,"Credencial de autenticação não é valida!"));

        APIException exception = assertThrows(APIException.class,
                () -> usuarioApplicationService.validarUsuario(emailUsuario, usuarioId));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusException());
        assertEquals("Credencial de autenticação não é valida!", exception.getMessage());
    }
}
