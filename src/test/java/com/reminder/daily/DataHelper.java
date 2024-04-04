package com.reminder.daily;

import com.reminder.daily.tarefa.application.api.TarefaRequest;
import com.reminder.daily.tarefa.application.api.TarefaResponse;
import com.reminder.daily.tarefa.domain.StatusTarefa;
import com.reminder.daily.tarefa.domain.Tarefa;
import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;
import com.reminder.daily.usuario.application.api.UsuarioNovoResponse;
import com.reminder.daily.usuario.domain.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataHelper {

    private static final UUID usuario1 = UUID.fromString("a713162f-20a9-4db9-a85b-90cd51ab18f4");
    private static final UUID idTarefa = UUID.fromString("06fb5521-9d5a-461a-82fb-e67e3bedc6eb");

    public static UsuarioNovoRequest getUsuarioRequest(){
        return new UsuarioNovoRequest("teste@teste.com", "123456");
    }

    public static Usuario getUsuario(){
        return Usuario.builder().email("teste@teste.com").idUsuario(usuario1).build();
    }
    public static UsuarioNovoResponse getUsuarioResponse(){
        return new UsuarioNovoResponse(getUsuario());
    }

    public static Tarefa criarTarefaAFazer(){
        return Tarefa.builder().idTarefa(idTarefa).status(StatusTarefa.A_FAZER).build();
    }
    public static Tarefa criarTarefaConcluida(){
        return Tarefa.builder().idTarefa(idTarefa).status(StatusTarefa.CONCLUIDA).build();
    }

    public static TarefaRequest getTarefaRequest(){
        return new TarefaRequest("teste", usuario1, StatusTarefa.A_FAZER, usuario1);
    }

    public static TarefaResponse getTarefaResponse(){
        return new TarefaResponse(criarTarefaAFazer());
    }

    public static List<Tarefa> getTarefasDoUsuario(UUID idUsuario){
        return List.of(Tarefa.builder().idTarefa(UUID.randomUUID()).idUsuario(idUsuario).descricao("teste1").build(),
                Tarefa.builder().idTarefa(UUID.randomUUID()).idUsuario(idUsuario).descricao("teste2").build(),
                Tarefa.builder().idTarefa(UUID.randomUUID()).idUsuario(idUsuario).descricao("teste3").build());
    }
}
