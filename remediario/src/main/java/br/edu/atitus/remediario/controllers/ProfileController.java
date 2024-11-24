package br.edu.atitus.remediario.controllers;

import br.edu.atitus.remediario.dtos.request.ProfileRequestDTO;
import br.edu.atitus.remediario.dtos.response.ProfileResponseDTO;
import br.edu.atitus.remediario.entities.ProfileEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;
import br.edu.atitus.remediario.security.TokenService;
import br.edu.atitus.remediario.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping("/select/{perfilId}")
    public ResponseEntity<UserEntity> selectProfile(@PathVariable UUID perfilId) {
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProfileEntity selectedProfile = perfilService.getProfileById(perfilId);
        if (selectedProfile == null || !selectedProfile.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body(null);
        }

        currentUser.setCurrentProfileId(perfilId);
        userRepository.save(currentUser);

        return ResponseEntity.ok(currentUser);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/select")
    public ResponseEntity<List<ProfileResponseDTO>> getUserProfiles() {
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProfileEntity> userProfiles = perfilService.getProfilesByUserId(currentUser.getId());
        
        List<ProfileResponseDTO> response = userProfiles.stream()
                .map(perfil -> new ProfileResponseDTO(perfil.getId(), perfil.getName()))
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
        perfilEntity.setUser(user);
        ProfileEntity savedPerfil = perfilService.savePerfil(perfilEntity);

        ProfileResponseDTO responseDto = new ProfileResponseDTO(perfilEntity.getId(), perfilEntity.getName());
        responseDto.setId(savedPerfil.getId());
        responseDto.setName(savedPerfil.getName());

        return ResponseEntity.ok(responseDto);
    }
}