package sapo.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pessoa {

    private String cpf;
    private String nome;
    private String[] habilidades;
    private List<Comentario> comentarios;

    public Pessoa(String cpf, String nome, String[] habilidades) {
        this.cpf = cpf;
        this.nome = nome;
        this.habilidades = habilidades;
        this.comentarios = new ArrayList<>();
    }

    @Override
    public String toString() {
        String presentation = this.nome + " – " + this.cpf;
        for (String habilidade : this.habilidades) {
            presentation += "\n- " + habilidade;
        }
        return presentation;
    }

    public String toStringReduzido() {
        return this.nome + " – " + this.cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setHabilidades(String[] habilidades) {
        this.habilidades = habilidades;
    }

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    public String listarComentarios() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.nome + " – " + this.cpf);
        builder.append("\nComentários:");
        for (Comentario c : this.comentarios) {
            builder.append("\n-- " + c.toString());
        }
        return builder.toString();
    }

    public boolean hasHabilidade(String habilidade) {
        return Arrays.asList(this.habilidades).contains(habilidade);
    }
}
