package br.edu.atitus.remediario.services;

import br.edu.atitus.remediario.entities.MedicamentoEntity;
import br.edu.atitus.remediario.entities.ProfileEntity;
import br.edu.atitus.remediario.repositories.MedicamentoRepository;
import br.edu.atitus.remediario.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService {

	@Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private ProfileRepository profileRepository;
    
    public List<MedicamentoEntity> getMedicamentosByProfileId(UUID profileId) {
        return medicamentoRepository.findByProfileId(profileId);
    }

    public MedicamentoEntity saveMedicamento(MedicamentoEntity medicamento, String userId) {
        Optional<ProfileEntity> perfilOptional = profileRepository.findByUserId(UUID.fromString(userId));
        
        if (perfilOptional.isEmpty()) {
            throw new RuntimeException("Perfil não encontrado para o usuário");
        }
        return medicamentoRepository.save(medicamento);
    }
}