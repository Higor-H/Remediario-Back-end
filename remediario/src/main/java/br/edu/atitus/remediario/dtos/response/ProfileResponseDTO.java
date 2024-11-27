package br.edu.atitus.remediario.dtos.response;

import java.util.UUID;

public class ProfileResponseDTO {
	
	private UUID id;
    private String name;
    private String bio;

    public ProfileResponseDTO(UUID id, String name, String bio) {
        this.id = id;
        this.name = name;
        this.bio = bio;
    }
    
    

    public String getBio() {
		return bio;
	}



	public void setBio(String bio) {
		this.bio = bio;
	}


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
