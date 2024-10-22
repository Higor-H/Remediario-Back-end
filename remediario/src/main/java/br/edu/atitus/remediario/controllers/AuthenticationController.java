package br.edu.atitus.remediario.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

@RestController()
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRequestDTO.email(), authenticationRequestDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        if(userRepository.findByEmail(registerRequestDTO.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.password());
        UserEntity user = new UserEntity(registerRequestDTO.email(), encryptedPassword, registerRequestDTO.role());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}