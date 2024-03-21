package com.reminder.daily.credencial.infra;

import com.reminder.daily.credencial.application.repository.CredencialRepository;
import com.reminder.daily.credencial.domain.Credencial;
import com.reminder.daily.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CredencialRepositoryMongoDB implements CredencialRepository {
    private final CredencialMongoSpringRepository credencialMongoSpringRepository;
    @Override
    public Credencial salva(Credencial credencial) {
        log.info("[start] CredencialRepositoryMongoDB - salva");
        credencialMongoSpringRepository.save(credencial);
        log.info("[finish] CredencialRepositoryMongoDB - salva");
        return credencial;
    }

    @Override
    public Credencial buscaCredencialPorUsuario(String usuario) {
        log.info("[start] CredencialRepositoryMongoDB - buscaCredencialPorUsuario");
        var credencial = credencialMongoSpringRepository.findByUsuario(usuario)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Não existe credencial para o Usuario informado!"));
        log.info("[finish] CredencialRepositoryMongoDB - buscaCredencialPorUsuario");
        return credencial;
    }
}
