package br.edu.atitus.remediario.dtos.request;

public class ProfileRequestDTO {
	
	private String name;
	
	private String bio;
	
	public ProfileRequestDTO() {
		
	}


	public String getBio() {
		return bio;
	}



	public void setBio(String bio) {
		this.bio = bio;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
