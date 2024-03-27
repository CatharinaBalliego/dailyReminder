package com.reminder.daily.usuario.domain;

import com.reminder.daily.usuario.application.api.UsuarioNovoRequest;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Email;
import java.util.UUID;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Document(collection = "Usuario")
public class Usuario {
    @Id
    private UUID idUsuario;
    @Email
    @Indexed(unique = true)
    private String email;

    public Usuario(UsuarioNovoRequest usuarioNovoRequest) {
        this.idUsuario =  UUID.randomUUID();
        this.email = usuarioNovoRequest.getEmail();
    }
}
