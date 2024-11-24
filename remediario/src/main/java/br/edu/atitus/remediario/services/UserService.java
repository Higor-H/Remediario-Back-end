package br.edu.atitus.remediario.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.atitus.remediario.entities.ProfileEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.repositories.UserRepository;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileService perfilService;
    @Autowired
    private PasswordEncoder encoder;

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    public void deleteUser(UUID userId) {
    	perfilService.deleteProfile(userId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));
        userRepository.delete(user);
    }

    
    public UserEntity saveUser(UserEntity user) {
        validarEmail(user.getEmail());
        validarSenha(user.getPassword());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        var perfis = perfilService.findByUser(user);
        if (perfis.isEmpty()) {
        	ProfileEntity perfil = new ProfileEntity();
        	perfil.setUser(user);
        	perfil.setName(user.getName());
        	perfilService.savePerfil(perfil);
        }
        ;
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


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		var user = this.userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		return user;
	}
}