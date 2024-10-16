package br.edu.atitus.remediario.controllers;

import br.edu.atitus.remediario.dtos.MedicamentoDto;
import br.edu.atitus.remediario.models.Medicamento;
import br.edu.atitus.remediario.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping("/medicamentos")
    public ResponseEntity<Medicamento> createMedicamento(@RequestBody MedicamentoDto medicamentoDto) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNome(medicamentoDto.getNome());
        medicamento.setDosagem(medicamentoDto.getDosagem());
        medicamento.setTipo(medicamentoDto.getTipo());
        medicamento.setQuantidade(medicamentoDto.getQuantidade());
        medicamento.setDescricao(medicamentoDto.getDescricao());
        
        Medicamento savedMedicamento = medicamentoService.saveMedicamento(medicamento);
        return ResponseEntity.ok(savedMedicamento);
    }
}
