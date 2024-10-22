package br.edu.atitus.remediario.repositories;

import br.edu.atitus.remediario.entities.MedicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, UUID> {
    
}
