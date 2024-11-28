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
    
    @Autowired
    private MedicamentoService medicamentoService;
    
    private Integer indice = 0;
    
    public boolean isProfileOwner(UUID profileId, UUID userId) {
        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado"));

        return profile.getUser().getId().equals(userId);
    }
    
    public ProfileEntity updateProfile(UUID profileId, String name, String bio) {
        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado"));

        profile.setName(name);
        profile.setBio(bio);
        
        return profileRepository.save(profile);
    }
    

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
    
    public void deleteProfileByUserId(UUID userId) {
    	List<ProfileEntity> userProfiles = getProfilesByUserId(userId);
    	for (ProfileEntity userProfile : userProfiles) {
    		medicamentoService.deleteMedicamentoByPerfilId(userProfile.getId());
    		profileRepository.delete(userProfile);
    	}
    }
    
    public void deleteProfileByProfileId(UUID profileId) {
    	ProfileEntity userProfiles = getProfileById(profileId);
    		medicamentoService.deleteMedicamentoByPerfilId(userProfiles.getId());
    		profileRepository.delete(userProfiles);
    }
}

