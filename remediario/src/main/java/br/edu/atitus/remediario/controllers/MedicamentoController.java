package br.edu.atitus.remediario.controllers;

import br.edu.atitus.remediario.dtos.request.MedicamentoRequestDTO;
import br.edu.atitus.remediario.entities.MedicamentoEntity;
import br.edu.atitus.remediario.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/novo")
    public ResponseEntity<MedicamentoEntity> createMedicamento(@RequestBody MedicamentoRequestDTO medicamentoDto) {
        MedicamentoEntity medicamento = new MedicamentoEntity();
        medicamento.setNome(medicamentoDto.getNome());
        medicamento.setDosagem(medicamentoDto.getDosagem());
        medicamento.setTipo(medicamentoDto.getTipo());
        medicamento.setQuantidade(medicamentoDto.getQuantidade());
        medicamento.setDescricao(medicamentoDto.getDescricao());
        
        MedicamentoEntity savedMedicamento = medicamentoService.saveMedicamento(medicamento);
        return ResponseEntity.ok(savedMedicamento);
    }
}
