package br.edu.atitus.remediario.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.atitus.remediario.entities.PerfilEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.PerfilRepository;

@Service
public class PerfilService {
	
    @Autowired
    private PerfilRepository perfilRepository;

    public PerfilEntity savePerfil(PerfilEntity perfil) {
        return perfilRepository.save(perfil);
    }
    
    public List<PerfilEntity> findByUser(UserEntity user) {
    	return perfilRepository.findByUser(user);
    }
    
    public List<PerfilEntity> getProfilesByUserId(UUID userId) {
        return perfilRepository.findAllByUserId(userId);
    }

}

