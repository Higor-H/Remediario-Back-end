package br.edu.atitus.remediario.dtos;

import java.util.UUID;

public class PerfilResponseDto {
	
	private UUID id;
    private String name;

    public PerfilResponseDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
