package br.edu.atitus.remediario.dtos.response;

public class SelectedProfileResponseDTO {
	
	private String name;
	private String id;
	
	public SelectedProfileResponseDTO(String name, String id) {
		this.name = name;
		this.id = id;
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}