package br.edu.atitus.remediario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.atitus.remediario.entities.UserEntity;  // Ajuste se necessário
import br.edu.atitus.remediario.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/create")
    public ResponseEntity<UserEntity> createUserByParams(
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam String name) {
        
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        
        UserEntity savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
