package com.reminder.daily.usuario.application.api;

import com.reminder.daily.usuario.domain.Usuario;

import lombok.Value;

import java.util.UUID;

@Value
public class UsuarioNovoResponse {
        private final UUID idUsuario;
        private final String email;


        public UsuarioNovoResponse(Usuario usuario) {
                this.idUsuario = usuario.getIdUsuario();
                this.email = usuario.getEmail();
        }
}
