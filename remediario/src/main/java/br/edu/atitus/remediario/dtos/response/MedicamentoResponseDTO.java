package br.edu.atitus.remediario.dtos.response;

import java.util.UUID;

public class MedicamentoResponseDTO {

    private UUID id;
    private String nome;
    private String dosagem;
    private String tipo;
    private Integer quantidade;
    private String descricao;


    public MedicamentoResponseDTO(UUID id, String nome, String dosagem, String tipo, Integer quantidade, String descricao) {
        this.id = id;
        this.nome = nome;
        this.dosagem = dosagem;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.descricao = descricao;
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
