package br.edu.atitus.remediario.dtos.response;


public class LoginResponseDTO {
    private String token;
    private String id;
    private String email;

    public LoginResponseDTO(String token, String id, String email) {
        this.token = token;
        this.id = id;
        this.email = email;
    }
    
    
    
    public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
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