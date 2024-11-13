package br.edu.atitus.remediario.dtos.request;


public class MedicamentoRequestDTO {
    
    private String nome;
    private String dosagem;
    private String tipo;
    private int quantidade;
	private String descricao;

    public MedicamentoRequestDTO() {}

    public MedicamentoRequestDTO( String nome, String dosagem, String tipo, int quantidade) {
        
        this.nome = nome;
        this.dosagem = dosagem;
        this.tipo = tipo;
        this.quantidade = quantidade;
    }

    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
