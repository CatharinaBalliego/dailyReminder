package com.reminder.daily.credencial.infra;

import com.reminder.daily.credencial.domain.Credencial;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CredencialMongoSpringRepository extends MongoRepository<Credencial, String> {
    Optional<Credencial> findByUsuario(String usuario);
}
