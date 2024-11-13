package br.edu.atitus.remediario.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.remediario.entities.ProfileEntity;
import br.edu.atitus.remediario.entities.UserEntity;


public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID>{

	List<ProfileEntity> findByUser(UserEntity user);
	
	List<ProfileEntity> findAllByUserId(UUID userId);
	
	public Optional<ProfileEntity> findByUserId(UUID userId);
	
}
