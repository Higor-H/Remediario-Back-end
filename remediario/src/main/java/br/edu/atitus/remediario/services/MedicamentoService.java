package br.edu.atitus.remediario.services;

import br.edu.atitus.remediario.entities.MedicamentoEntity;
import br.edu.atitus.remediario.repositories.MedicamentoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService {

	@Autowired
    private MedicamentoRepository medicamentoRepository;
    
    
    private Integer indice = 0;
    
    public List<MedicamentoEntity> getMedicamentosByProfileId(UUID profileId) {
        return medicamentoRepository.findByProfileId(profileId);
    }

    public MedicamentoEntity saveMedicamento(MedicamentoEntity medicamento) {
   
        return medicamentoRepository.save(medicamento);
    }
    
    public int deleteMedicamento(UUID perfilId) {
    	List<MedicamentoEntity> medicamentos = getMedicamentosByProfileId(perfilId);
    	for (MedicamentoEntity loop : medicamentos) {
    		medicamentoRepository.delete(medicamentos.get(indice));
    		indice = indice+1;
    	}
    	return indice = 0;
    }
    public void deleteMedicamentoById(UUID medId) {
        Optional<MedicamentoEntity> medicamento = medicamentoRepository.findById(medId);
        if (medicamento.isPresent()) {
            medicamentoRepository.delete(medicamento.get());
        } else {
            throw new IllegalArgumentException("Medicamento com ID " + medId + " não encontrado.");
        }
    }
    
    public MedicamentoEntity getMedicamentoById(UUID medId) {
        return medicamentoRepository.findById(medId)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento com ID " + medId + " não encontrado."));
    }
}