package br.edu.atitus.remediario.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import br.edu.atitus.remediario.dtos.request.AuthenticationRequestDTO;
import br.edu.atitus.remediario.dtos.request.RegisterRequestDTO;
import br.edu.atitus.remediario.dtos.response.LoginResponseDTO;
import br.edu.atitus.remediario.dtos.response.RegisterResponseDTO;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;
import br.edu.atitus.remediario.security.TokenService;
import br.edu.atitus.remediario.services.CloudinaryService;
import br.edu.atitus.remediario.services.UserService;



@RestController()
@RequestMapping("auth")
public class AuthenticationController {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationConfiguration auth;
    @Autowired
    private CloudinaryService cloudinaryService;
    
     
    @GetMapping("/{userId}/profile-image")
    public ResponseEntity<String> getProfileImage(@PathVariable UUID userId) {
        String imageUrl = userService.getUserProfileImage(userId);

        if (imageUrl == null) {
            return ResponseEntity.status(404).body("Imagem de perfil não encontrada.");
        }

        return ResponseEntity.ok(imageUrl);
    }

    @PostMapping("/{userId}/upload-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable UUID userId, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Arquivo não pode ser vazio.");
            }

            String fileName = userId + "_" + file.getOriginalFilename();
            String fileUrl = cloudinaryService.uploadFile(file.getBytes(), fileName);

            userService.updateUserProfileImage(userId, fileUrl);
            return ResponseEntity.ok("Imagem carregada com sucesso");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a imagem.");
        }
    }

    

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            auth.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    authenticationRequestDTO.email(),
                    authenticationRequestDTO.password()
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        var user = userService.loadUserByUsername(authenticationRequestDTO.email());

        String token = tokenService.generateToken((UserEntity) user);
        String userId = tokenService.getUserIdFromToken(token);
        String email = authenticationRequestDTO.email(); // O email já está disponível aqui

        return ResponseEntity.ok(new LoginResponseDTO(token, userId, email));
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já registrado");
        }

        UserEntity user = new UserEntity();
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(registerRequestDTO.getPassword());
        user.setName(registerRequestDTO.getName());

        try {
            userService.saveUser(user);
            RegisterResponseDTO responseDto = new RegisterResponseDTO(user.getEmail(),user.getName());
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
        String userId = tokenService.getUserIdFromToken(token.replace("Bearer ", ""));
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        try {
            userService.deleteUser(UUID.fromString(userId));
            return ResponseEntity.ok("Usuário e dados vinculados excluídos com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o usuário: " + e.getMessage());
        }
    }
}