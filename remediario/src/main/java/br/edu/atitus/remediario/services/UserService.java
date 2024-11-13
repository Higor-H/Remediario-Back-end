package br.edu.atitus.remediario.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.atitus.remediario.entities.ProfileEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileService perfilService;

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    public UserEntity saveUser(UserEntity user) {
        validarEmail(user.getEmail());
        validarSenha(user.getPassword());
        userRepository.save(user);
        var perfis = perfilService.findByUser(user);
        if (perfis.isEmpty()) {
        	ProfileEntity perfil = new ProfileEntity();
        	perfil.setUser(user);
        	perfil.setName(user.getName());
        	perfilService.savePerfil(perfil);
        }
        return user;
    }

    private void validarSenha (String senha) {
    	if (senha == null || senha.length() < 8 || !senha.matches(".*[A-Z].*.*[a-z].*.*[!@#$%^&*(),.?\\\":{}|<>].*")) {
    		throw new IllegalArgumentException("Senha inválida! A senha deve seguir o formato correto.");
    	}
    }

    private void validarEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido! O email deve seguir o formato correto.");
        }
    }
}