package sapo.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Pessoa {

    private String cpf;
    private String nome;
    private String[] habilidades;
    private int nivel;
    private Optional<Funcao> funcao;
    private List<Comentario> comentarios;

    public Pessoa(String cpf, String nome, String[] habilidades) {
        this.cpf = cpf;
        this.nome = nome;
        this.nivel = 0;
        this.funcao = Optional.empty();
        this.habilidades = habilidades;
        this.comentarios = new ArrayList<>();
    }

    @Override
    public String toString() {
        String presentation = this.nome + " – " + this.cpf + "\n";
        if (this.funcao.isPresent()) {
            presentation += this.funcao.get().gerarDetalhes();
        }
        for (String habilidade : this.habilidades) {
            presentation += "\n- " + habilidade;
        }
        return presentation;
    }

    public String toStringReduzido() {
        return this.nome + " – " + this.cpf;
    }

    public void setFuncaoAluno(String matricula, int periodo) {
        if (this.funcao.isPresent() && this.funcao.get() instanceof Aluno) {
            throw new IllegalStateException("Essa pessoa já é um Aluno");
        }
        this.funcao = Optional.of(new Aluno(matricula, periodo));

    }

    public void setFuncaoProfessor(String siape, String[] disciplinas) {
        if (this.funcao.isPresent() && this.funcao.get() instanceof Professor) {
            throw new IllegalStateException("Essa pessoa já é um Professor");
        }
        this.funcao = Optional.of(new Professor(siape, disciplinas));
    }

    public void removerFuncao() {
        this.funcao = Optional.empty();
    }



    public String getNome() {
        return this.nome;
    }

    public int getNivel() {
        return this.nivel;
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
}
