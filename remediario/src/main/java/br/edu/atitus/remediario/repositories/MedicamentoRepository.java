package br.edu.atitus.remediario.repositories;

import br.edu.atitus.remediario.entities.MedicamentoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, UUID> {
	
	List<MedicamentoEntity> findByProfileId(UUID profileId);
	
}
