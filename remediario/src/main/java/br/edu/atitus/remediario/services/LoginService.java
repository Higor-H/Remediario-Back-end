package br.edu.atitus.remediario.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.LoginRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public boolean authenticateUser(String email, String password) {
        UserEntity user = loginRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
    
}
