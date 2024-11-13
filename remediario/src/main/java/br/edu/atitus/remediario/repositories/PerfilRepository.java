package br.edu.atitus.remediario.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.remediario.entities.PerfilEntity;
import br.edu.atitus.remediario.entities.UserEntity;


public interface PerfilRepository extends JpaRepository<PerfilEntity, UUID>{

	List<PerfilEntity> findByUser(UserEntity user);
	
	List<PerfilEntity> findAllByUserId(UUID userId);
	
}
