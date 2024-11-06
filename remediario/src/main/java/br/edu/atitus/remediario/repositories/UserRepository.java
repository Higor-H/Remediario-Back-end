package br.edu.atitus.remediario.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import br.edu.atitus.remediario.entities.UserEntity;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	Optional<UserEntity> findByEmail(String email);

}
