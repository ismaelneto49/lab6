package sapo.entities;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {

    private String cpf;
    private String nome;
    private String[] habilidades;
    private int nivel;
    private Optional<Funcao> funcao;
    private List<Comentario> comentarios;
    private Map<String, Tarefa> tarefas;
    private Map<String, Tarefa> tarefasContabilizadas;

    public Pessoa(String cpf, String nome, String[] habilidades) {
        this.cpf = cpf;
        this.nome = nome;
        this.nivel = 0;
        this.funcao = Optional.empty();
        this.habilidades = habilidades;
        this.comentarios = new ArrayList<>();
        this.tarefasContabilizadas = new HashMap<>();
        this.tarefas = new HashMap<>();
    }

    public void setFuncaoAluno(String matricula, int periodo) {
        if (this.funcao.isPresent() && this.funcao.get() instanceof Aluno) {
            throw new IllegalStateException("Essa pessoa já é um Aluno");
        }
        this.nivel += this.calcularNivel();
        this.contabilizarTarefas();
        this.funcao = Optional.of(new Aluno(matricula, periodo));

    }

    public void setFuncaoProfessor(String siape, String[] disciplinas) {
        if (this.funcao.isPresent() && this.funcao.get() instanceof Professor) {
            throw new IllegalStateException("Essa pessoa já é um Professor");
        }
        this.nivel += this.calcularNivel();
        this.contabilizarTarefas();
        this.funcao = Optional.of(new Professor(siape, disciplinas));
    }

    public void removerFuncao() {
        if (this.funcao.isEmpty()) {
            throw new IllegalStateException("Essa pessoa já está sem função");
        }
        this.nivel += this.calcularNivel();
        this.contabilizarTarefas();
        this.funcao = Optional.empty();
    }

    public void adicionarTarefa(Tarefa tarefa) {
        this.tarefas.put(tarefa.getId(), tarefa);
    }

    public void removerTarefa(Tarefa tarefa) {
        this.tarefas.remove(tarefa.getId());
    }

    public String getNome() {
        return this.nome;
    }

    public int getNivel() {
        return this.nivel + this.calcularNivel();
    }

    public String[] getHabilidades() {
        return this.habilidades;
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

    private int calcularNivel() {
        Map<String, Tarefa> tarefasASeremContabilizadas = this.calcularTarefasASeremContabilizadas();

        if (this.funcao.isPresent()) {
            return this.funcao.get().incrementarNivel(tarefasASeremContabilizadas, this);
        }

        return this.incrementarNivel(tarefasASeremContabilizadas);

    }

    private void contabilizarTarefas() {
        Map<String, Tarefa> tarefasASeremContabilizadas = this.calcularTarefasASeremContabilizadas();
        this.tarefasContabilizadas.putAll(tarefasASeremContabilizadas);

    }

    private Map<String, Tarefa> calcularTarefasASeremContabilizadas() {
        Map<String, Tarefa> tarefasASeremContabilizadas = new HashMap<>();

        for (Tarefa tarefa : this.tarefas.values()) {
            if (!this.tarefasContabilizadas.containsKey(tarefa.getId())) {
                tarefasASeremContabilizadas.put(tarefa.getId(), tarefa);
            }
        }

        return tarefasASeremContabilizadas;
    }

    private int incrementarNivel(Map<String, Tarefa> tarefas) {
        double nivelTotal = 0;

        for (Tarefa tarefa : tarefas.values()) {
            if (tarefa.getIsConcluida()) {
                nivelTotal += 1;
            }
            nivelTotal += 0.5;
        }
        return (int) nivelTotal;

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

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa pessoa = (Pessoa) object;

        return this.cpf == pessoa.cpf;
    }

    public boolean hasHabilidade(String habilidade) {
        return Arrays.asList(this.habilidades).contains(habilidade);
    }
}
