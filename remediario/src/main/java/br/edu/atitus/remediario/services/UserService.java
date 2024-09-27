package br.edu.atitus.remediario.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.atitus.remediario.entities.UserEntity; // Altere para importar UserEntity
import br.edu.atitus.remediario.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity saveUser(UserEntity user) { // Altere para UserEntity
        return userRepository.save(user);
    }
}
