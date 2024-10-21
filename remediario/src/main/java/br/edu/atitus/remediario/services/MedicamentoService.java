package br.edu.atitus.remediario.services;

import br.edu.atitus.remediario.entities.MedicamentoEntity;
import br.edu.atitus.remediario.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public MedicamentoEntity saveMedicamento(MedicamentoEntity medicamentoEntity) {
        return medicamentoRepository.save(medicamentoEntity);
    }
}
