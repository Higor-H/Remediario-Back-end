package br.edu.atitus.remediario.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.remediario.dtos.AuthenticationRequestDTO;
import br.edu.atitus.remediario.dtos.RegisterRequestDTO;
import br.edu.atitus.remediario.dtos.LoginResponseDTO;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;
import br.edu.atitus.remediario.security.TokenService;
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

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO) {
        UserEntity user = userRepository.findByEmail(authenticationRequestDTO.email())
                .orElse(null);

        if (user == null || !user.getPassword().equals(authenticationRequestDTO.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        var token = tokenService.generateToken(user);
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

        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}