package br.edu.atitus.remediario.dtos.response;


public class LoginResponseDTO {
    private String token;
    private String id;

    public LoginResponseDTO(String token, String id) {
        this.token = token;
        this.id = id;
    }
    
    
    
    public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
