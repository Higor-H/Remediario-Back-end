package br.edu.atitus.remediario.repositories;

import br.edu.atitus.remediario.models.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, UUID> {
    
}
