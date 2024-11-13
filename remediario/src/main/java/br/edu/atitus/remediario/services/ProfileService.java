package br.edu.atitus.remediario.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.atitus.remediario.entities.ProfileEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.ProfileRepository;

@Service
public class ProfileService {
	
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileEntity savePerfil(ProfileEntity perfil) {
        return profileRepository.save(perfil);
    }
    
    public List<ProfileEntity> findByUser(UserEntity user) {
    	return profileRepository.findByUser(user);
    }
    
    public List<ProfileEntity> getProfilesByUserId(UUID userId) {
        return profileRepository.findAllByUserId(userId);
    }
    
    public ProfileEntity getProfileById(UUID perfilId) {
        Optional<ProfileEntity> optionalProfile = profileRepository.findById(perfilId);
        return optionalProfile.orElse(null);
    }

}

