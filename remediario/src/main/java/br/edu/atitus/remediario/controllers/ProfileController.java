package br.edu.atitus.remediario.controllers;

import br.edu.atitus.remediario.dtos.request.ProfileRequestDTO;
import br.edu.atitus.remediario.dtos.response.ProfileResponseDTO;
import br.edu.atitus.remediario.dtos.response.SelectedProfileResponseDTO;
import br.edu.atitus.remediario.entities.ProfileEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;
import br.edu.atitus.remediario.security.TokenService;
import br.edu.atitus.remediario.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    
    @Autowired
    private ProfileService perfilService;
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;
    
    @DeleteMapping("/delete/{profileId}")
    public ResponseEntity<String> deleteProfile(@PathVariable UUID profileId) {
        try {
            UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            
            if (perfilService.isProfileOwner(profileId, currentUser.getId())) {
                perfilService.deleteProfileByProfileId(profileId);
                return ResponseEntity.ok("Perfil deletado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não autorizado a excluir esse perfil");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir o perfil: " + e.getMessage());
        }
    }
    
    @PutMapping("/edit/{profileId}")
    public ResponseEntity<String> updateProfile(
            @PathVariable UUID profileId, 
            @RequestBody ProfileRequestDTO profileRequestDTO) {
        try {
            perfilService.updateProfile(profileId, profileRequestDTO.getName(), profileRequestDTO.getBio());
            return ResponseEntity.ok("Perfil atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao atualizar perfil: " + e.getMessage());
        }
    }
    
    @PostMapping("/select/{perfilId}")
    public ResponseEntity<Object> selectProfile(@PathVariable UUID perfilId) {
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProfileEntity selectedProfile = perfilService.getProfileById(perfilId);
        if (selectedProfile == null || !selectedProfile.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body(null);
        }

        currentUser.setCurrentProfileId(perfilId);
        userRepository.save(currentUser);
        SelectedProfileResponseDTO responseDto = new SelectedProfileResponseDTO(selectedProfile.getName(),selectedProfile.getId().toString());
        
        return ResponseEntity.ok(responseDto);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/select")
    public ResponseEntity<List<ProfileResponseDTO>> getUserProfiles() {
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProfileEntity> userProfiles = perfilService.getProfilesByUserId(currentUser.getId());
        
        List<ProfileResponseDTO> response = userProfiles.stream()
                .map(perfil -> new ProfileResponseDTO(perfil.getId(), perfil.getName(), perfil.getBio()))
                .toList();

        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<ProfileResponseDTO> createPerfil(@RequestBody ProfileRequestDTO perfilDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userId = tokenService.getUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(403).body(null);
        }

        UserEntity user = userRepository.findById(UUID.fromString(userId)).orElse(null);
        
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        ProfileEntity perfilEntity = new ProfileEntity();
        perfilEntity.setName(perfilDto.getName());
        perfilEntity.setBio(perfilDto.getBio());
        perfilEntity.setUser(user);
        
        ProfileEntity savedPerfil = perfilService.savePerfil(perfilEntity);

        ProfileResponseDTO responseDto = new ProfileResponseDTO(perfilEntity.getId(), perfilEntity.getName(), perfilEntity.getBio());
        responseDto.setId(savedPerfil.getId());
        responseDto.setName(savedPerfil.getName());
        responseDto.setBio(savedPerfil.getBio());

        return ResponseEntity.ok(responseDto);
    }
}