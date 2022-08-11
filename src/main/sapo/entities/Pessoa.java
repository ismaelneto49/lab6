package sapo.entities;

import java.util.ArrayList;
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

    public String toStringReduced() {
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
        String apresentacao = this.nome + " – " + this.cpf + "\nComentários:";
        for (Comentario c : this.comentarios) {
            apresentacao += "\n-- " + c.toString();
        }
        return apresentacao;
    }
}
