package com.reminder.daily.usuario.application.api;

import com.reminder.daily.usuario.domain.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequestMapping(value = "/public/v1/usuario")
public interface UsuarioAPI {
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    UsuarioNovoResponse postNovoUsuario(@RequestBody @Valid UsuarioNovoRequest usuarioNovoRequest);
}
