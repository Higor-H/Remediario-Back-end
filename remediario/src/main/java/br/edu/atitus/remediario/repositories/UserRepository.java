package br.edu.atitus.remediario.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.atitus.remediario.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    // Exemplo de método para buscar um usuário por e-mail
    Optional<UserEntity> findByEmail(String email);
}
