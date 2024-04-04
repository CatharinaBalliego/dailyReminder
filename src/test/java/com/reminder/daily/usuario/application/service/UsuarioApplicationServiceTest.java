package com.reminder.daily.usuario.application.service;

import com.reminder.daily.DataHelper;
import com.reminder.daily.credencial.application.service.CredencialService;
import com.reminder.daily.handler.APIException;
import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;
import com.reminder.daily.usuario.application.api.UsuarioNovoResponse;
import com.reminder.daily.usuario.application.repository.UsuarioRepository;
import com.reminder.daily.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


//

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
        UsuarioNovoResponse usuarioResponse = DataHelper.getUsuarioResponse();
        Usuario usuario = new Usuario(usuarioRequest);

        credencialService.criarNovaCredencial(usuarioRequest);

        when(usuarioRepository.salva(any(Usuario.class))).thenReturn(usuario);
        usuarioApplicationService.salvarUsuario(usuarioRequest);

        verify(usuarioRepository, times(1)).salva(usuario);


        assertNotNull(usuarioResponse);
        assertEquals(UsuarioNovoResponse.class, usuarioResponse.getClass());
        assertEquals(UUID.class, usuarioResponse.getIdUsuario().getClass());
    }
//
//    @Test
//    public void salvarUsuario_emailDuplicado_Conflict(){
//        Usuario usuario = DataHelper.criarUsuario();
//        UsuarioNovoRequest usuarioNovoRequest = DataHelper.criarUsuarioRequest();
//        when(usuarioRepository.salva(usuario)).thenThrow(APIException.class);
//        //when(usuarioRepository.salva(usuario)).thenThrow(MongoWriteException.class);
//        APIException exception = assertThrows(APIException.class,
//                () -> usuarioApplicationService.salvarUsuario(usuarioNovoRequest));
//        assertEquals("Usuario já está cadastrado no sistema!",exception.getMessage());
//
//    }

    @Test
    public void buscarUsuarioPorId_valido_UsuarioResponse(){
        UUID usuarioId = UUID.fromString("a713162f-20a9-5db9-a85b-90cd52ab18f4");
        Usuario usuario = DataHelper.getUsuario();
        UsuarioNovoResponse usuarioResponse = DataHelper.getUsuarioResponse();
        Mockito.lenient().when(usuarioRepository.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);

        assertNotNull(usuarioResponse);
        assertEquals(UsuarioNovoResponse.class, usuarioResponse.getClass());
        assertEquals(UUID.class, usuarioResponse.getIdUsuario().getClass());
    }

    @Test
    public void buscarUsuarioPorId_idInvalido_NotFound(){
        Usuario usuario = DataHelper.getUsuario();

       // when(usuarioRepository.buscarUsuarioPorId(eq(usuarioId))).thenThrow(APIException.class);
        when(usuarioRepository.buscarUsuarioPorId(any())).thenReturn(usuario);

        //doThrow(APIException.class).when(usuarioRepository.buscarUsuarioPorId(any()));

        APIException exception = assertThrows(APIException.class,
                () -> usuarioApplicationService.buscarUsuarioPorId(UUID.randomUUID()));

        assertEquals("Usuario não encontrado!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
    }


    //Validar usuario
}
