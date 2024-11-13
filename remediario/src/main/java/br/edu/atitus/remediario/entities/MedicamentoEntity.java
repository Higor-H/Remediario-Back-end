
package br.edu.atitus.remediario.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_medicamento")
public class MedicamentoEntity {
	
	@Id
    @GeneratedValue(generator = "UUID")
	@Column(name = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String dosagem;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private int quantidade;
    
    @Column(nullable = false)
    private String descricao;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "perfil_id")
    private ProfileEntity profile;

    
    public MedicamentoEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProfileEntity getProfile() {
		return profile;
	}

	public void setProfile(ProfileEntity perfil) {
		this.profile = perfil;
	}

}
