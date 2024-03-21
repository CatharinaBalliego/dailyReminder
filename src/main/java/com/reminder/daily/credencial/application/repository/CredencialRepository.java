package com.reminder.daily.credencial.application.repository;

import com.reminder.daily.credencial.domain.Credencial;

public interface CredencialRepository {
    Credencial salva(Credencial credencial);
    Credencial buscaCredencialPorUsuario(String usuario);
}
