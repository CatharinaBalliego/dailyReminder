package com.reminder.daily.RabbitMQ.application.api;

import com.reminder.daily.usuario.domain.Usuario;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class EmailRequest implements Serializable {
    @NotBlank
    private String ownerRef;
    @NotBlank
    @Email
    private String emailFrom;
    @NotBlank
    @Email
    private String emailTo;
    @NotBlank
    private String subject;
    @NotBlank
    private String content;

    public EmailRequest(Usuario usuario) {
        this.ownerRef = usuario.getIdUsuario().toString();
        this.emailFrom = "";
        this.emailTo = usuario.getEmail();
        this.subject = "Confirme o seu email";
        this.content = "teste";
    }
}