package br.edu.atitus.remediario.controllers;

import br.edu.atitus.remediario.dtos.PerfilDto;
import br.edu.atitus.remediario.entities.PerfilEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;
import br.edu.atitus.remediario.security.TokenService;
import br.edu.atitus.remediario.services.PerfilService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class PerfilController {
    
    @Autowired
    private PerfilService perfilService;
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<PerfilEntity> createPerfil(@RequestBody PerfilDto perfilDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userId = tokenService.getUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(403).body(null);
        }

        UserEntity user = userRepository.findById(UUID.fromString(userId)).orElse(null);
        
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        PerfilEntity perfilEntity = new PerfilEntity();
        perfilEntity.setName(perfilDto.getName());
        perfilEntity.setUser(user);
        PerfilEntity savedPerfil = perfilService.savePerfil(perfilEntity);
        return ResponseEntity.ok(savedPerfil);
    }
}
