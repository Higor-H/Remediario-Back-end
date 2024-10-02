package br.edu.atitus.remediario.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.atitus.remediario.entities.UserEntity;

@Repository
public interface LoginRepository extends JpaRepository<UserEntity, UUID> {

	UserEntity findByEmail(String email);
}
