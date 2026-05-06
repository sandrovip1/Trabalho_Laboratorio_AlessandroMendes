package br.edu.ifpa.laboratorio.model;

public class Equipamento {
    private int id;
    private String nome;
    private String tipo;
    private boolean disponivel;

    public Equipamento(int id, String nome, String tipo, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.disponivel = disponivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
