package br.edu.atitus.remediario.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean loadUserByUsername(String username, String password) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return userEntity.getPassword().equals(password);
    }
}
