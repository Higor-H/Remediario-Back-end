package br.edu.atitus.remediario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; // Injetar corretamente o serviço

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserEntity user) {
        ResponseEntity<Object> savedUserResponse = userService.saveUser(user);
        return savedUserResponse; // Retornar a resposta gerada pelo serviço
    }
}
