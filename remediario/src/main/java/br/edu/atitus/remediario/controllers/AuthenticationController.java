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

@RestController()
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO) {
        UserEntity user = new UserEntity();
        var token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        if(userRepository.findByEmail(registerRequestDTO.email()) != null)
        	return ResponseEntity.badRequest().build();

        UserEntity user = new UserEntity();
        user.setEmail(registerRequestDTO.email());
        user.setPassword(registerRequestDTO.password());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}