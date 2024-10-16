package br.edu.atitus.remediario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserEntity user) {
        
        	if (user.getEmail().contains("@")) {
        		userService.saveUser(user);
        		return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
        	}
            
        	else {
            return new ResponseEntity<>("Error creating user", HttpStatus.BAD_REQUEST);
        	}
    
    }
}
