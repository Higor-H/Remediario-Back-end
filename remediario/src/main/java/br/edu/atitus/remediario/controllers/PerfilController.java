package br.edu.atitus.remediario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.remediario.dtos.PerfilDto;
import br.edu.atitus.remediario.entities.PerfilEntity;
import br.edu.atitus.remediario.services.PerfilService;

@RestController
public class PerfilController {
	
	   @Autowired
	    private PerfilService perfilService;

	    @PostMapping("/perfil")
	    public ResponseEntity<PerfilEntity> createPerfil(@RequestBody PerfilDto perfilDto) {
	        PerfilEntity perfilEntity = new PerfilEntity();
	        perfilEntity.setName(perfilDto.getName());
	        
	        PerfilEntity savedPerfil = perfilService.savePerfil(perfilEntity);
	        return ResponseEntity.ok(savedPerfil);
	    }
}
