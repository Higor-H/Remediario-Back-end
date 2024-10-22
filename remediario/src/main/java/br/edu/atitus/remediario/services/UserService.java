package br.edu.atitus.remediario.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.atitus.remediario.entities.PerfilEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PerfilService perfilService;

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public UserEntity saveUser(UserEntity userEntity) {
        validarEmail(userEntity.getEmail());
        validarSenha(userEntity.getPassword());
        userRepository.save(userEntity);
        var perfis = perfilService.findByUser(userEntity);
        if (perfis.isEmpty()) {
        	PerfilEntity perfil = new PerfilEntity();
        	perfil.setUser(userEntity);
        	perfilService.savePerfil(perfil);
        }
        return userEntity;
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
