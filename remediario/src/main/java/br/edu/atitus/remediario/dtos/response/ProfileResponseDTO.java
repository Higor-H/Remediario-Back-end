package br.edu.atitus.remediario.dtos.response;

import java.util.UUID;

public class ProfileResponseDTO {
	
	private UUID id;
    private String name;

    public ProfileResponseDTO(UUID id, String name) {
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
