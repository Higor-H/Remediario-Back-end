package br.edu.atitus.remediario.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.remediario.dtos.request.AuthenticationRequestDTO;
import br.edu.atitus.remediario.dtos.request.RegisterRequestDTO;
import br.edu.atitus.remediario.dtos.response.LoginResponseDTO;
import br.edu.atitus.remediario.dtos.response.RegisterResponseDTO;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;
import br.edu.atitus.remediario.security.TokenService;
import br.edu.atitus.remediario.services.UserService;
import jakarta.validation.Valid;

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO) {
    	try {
			auth.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.email(), authenticationRequestDTO.password()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
    	var user = userService.loadUserByUsername(authenticationRequestDTO.email());
  
        String token = tokenService.generateToken((UserEntity) user);
        return ResponseEntity.ok(new LoginResponseDTO(token));
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
