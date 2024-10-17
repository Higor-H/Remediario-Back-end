package br.edu.atitus.remediario.models;

import java.util.UUID;

import jakarta.persistence.Id;


public class Perfil {


	@Id
	private UUID id;
    private String username;
    
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return username;
	}
	public void setName(String username) {
		this.username = username;
	}
	
}