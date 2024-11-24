package br.edu.atitus.remediario.controllers;

import br.edu.atitus.remediario.dtos.request.MedicamentoRequestDTO;
import br.edu.atitus.remediario.dtos.response.MedicamentoResponseDTO;
import br.edu.atitus.remediario.entities.MedicamentoEntity;
import br.edu.atitus.remediario.entities.ProfileEntity;
import br.edu.atitus.remediario.entities.UserEntity;
import br.edu.atitus.remediario.security.TokenService;
import br.edu.atitus.remediario.services.MedicamentoService;
import br.edu.atitus.remediario.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicamento")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private ProfileService profileService;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list")
    public ResponseEntity<List<MedicamentoResponseDTO>> getMedicamentosByProfile(HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getCurrentProfileId() == null) {
            return ResponseEntity.status(400).body(null);
        }

        ProfileEntity activeProfile = profileService.getProfileById(currentUser.getCurrentProfileId());
        if (activeProfile == null || !activeProfile.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body(null);
        }

        List<MedicamentoEntity> medicamentos = medicamentoService.getMedicamentosByProfileId(activeProfile.getId());
        
        List<MedicamentoResponseDTO> medicamentoDTOs = medicamentos.stream()
                .map(medicamento -> new MedicamentoResponseDTO(
                        medicamento.getId(),
                        medicamento.getNome(),
                        medicamento.getDosagem(),
                        medicamento.getTipo(),
                        medicamento.getQuantidade(),
                        medicamento.getDescricao()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(medicamentoDTOs);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<String> createMedicamento(@RequestBody MedicamentoRequestDTO medicamentoDto, HttpServletRequest request) {
    	UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getCurrentProfileId() == null) {
            return ResponseEntity.status(400).body("Nenhum perfil ativo selecionado.");
        }

        ProfileEntity activeProfile = profileService.getProfileById(currentUser.getCurrentProfileId());
        if (activeProfile == null || !activeProfile.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body("Perfil ativo inválido ou não pertence ao usuário.");
        }

        MedicamentoEntity medicamento = new MedicamentoEntity();
        medicamento.setProfile(activeProfile);
    
    	String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userId = tokenService.getUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(403).body(null);
        }
    
        medicamento.setNome(medicamentoDto.getNome());
        medicamento.setDosagem(medicamentoDto.getDosagem());
        medicamento.setTipo(medicamentoDto.getTipo());
        medicamento.setQuantidade(medicamentoDto.getQuantidade());
        medicamento.setDescricao(medicamentoDto.getDescricao());
        medicamentoService.saveMedicamento(medicamento);
        return ResponseEntity.ok("medicamento cadastrado");
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/deletebyid/{medId}")
    public ResponseEntity<String> deleteMedicamentoById(@PathVariable UUID medId, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getCurrentProfileId() == null) {
            return ResponseEntity.status(400).body("Nenhum perfil ativo selecionado.");
        }

        ProfileEntity activeProfile = profileService.getProfileById(currentUser.getCurrentProfileId());
        if (activeProfile == null || !activeProfile.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body("Perfil ativo inválido ou não pertence ao usuário.");
        }

        medicamentoService.deleteMedicamentoById(medId);
        return ResponseEntity.ok("Medicamento deletado com sucesso.");
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getbyid/{medId}")
    public ResponseEntity<MedicamentoResponseDTO> getMedicamentoById(@PathVariable UUID medId, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getCurrentProfileId() == null) {
            return ResponseEntity.status(400).body(null);
        }

        ProfileEntity activeProfile = profileService.getProfileById(currentUser.getCurrentProfileId());
        if (activeProfile == null || !activeProfile.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body(null);
        }

        MedicamentoEntity medicamento = medicamentoService.getMedicamentoById(medId);

        if (!medicamento.getProfile().getId().equals(activeProfile.getId())) {
            return ResponseEntity.status(403).body(null);
        }

        MedicamentoResponseDTO medicamentoDto = new MedicamentoResponseDTO(
                medicamento.getId(),
                medicamento.getNome(),
                medicamento.getDosagem(),
                medicamento.getTipo(),
                medicamento.getQuantidade(),
                medicamento.getDescricao());

        return ResponseEntity.ok(medicamentoDto);
    }

}
