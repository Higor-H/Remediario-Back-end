package br.edu.atitus.remediario.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.remediario.entities.PerfilEntity;


public interface PerfilRepository extends JpaRepository<PerfilEntity, UUID>{

}
