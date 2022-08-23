package sapo.entities;

import java.util.*;

public class TarefaGerencial {

    private String id;
    private String nome;
    private int duracao;
    private Atividade atividade;
    private boolean isConcluida;
    private Set<Pessoa> pessoasResponsaveis;
    private List<Tarefa> subtarefas;

    public TarefaGerencial(String id, String nome, int duracao, Atividade atividade) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
        this.atividade = atividade;
        this.pessoasResponsaveis = new HashSet<>();
        this.subtarefas = new ArrayList<>();
    }

    public int contarTarefas() {
        return this.subtarefas.size();
    }

    public void removerTarefa(Tarefa tarefa) {
        this.subtarefas.remove(tarefa);
    }

    public void addSubtarefa(Tarefa t) {
        this.subtarefas.add(t);
    }

    public int getDuracao() {
        return this.duracao;
    }

    public String[] getHabilidades() {
        Set<String> habilidades = new HashSet<>();
        habilidades.add("gestão");
        for (Tarefa t : this.subtarefas) {
            habilidades.addAll(Arrays.asList(t.getHabilidades()));
        }
        return habilidades.toArray(new String[]{});
    }

    public boolean getIsConcluida() {
        return this.isConcluida;
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public Set<Pessoa> getPessoasResponsaveis() {
        return this.pessoasResponsaveis;
    }

    public void concluirTarefa() {
        if (this.isConcluida) {
            throw new IllegalStateException("A tarefa já está concluída");
        }
        for (Tarefa t : this.subtarefas) {
            if (t.getIsConcluida()) {
                continue;
            } else {
                t.concluirTarefa();
            }
        }
        this.isConcluida = true;
    }

    public void adicionarPessoaResponsavel(Pessoa pessoa) {
        if (this.isConcluida) {
            throw new IllegalStateException("Não é possível adicionar pessoa responsável em uma tarefa já concluída");
        }
        this.pessoasResponsaveis.add(pessoa);
    }

    public void removerPessoaResponsavel(Pessoa pessoa) {
        if (this.isConcluida) {
            throw new IllegalStateException("Não é possível remover pessoa responsável em uma tarefa já concluída");
        }
        this.pessoasResponsaveis.remove(pessoa);
    }

    public void adicionarHoras(int horas) {
        if (horas < 0) {
            throw new IllegalArgumentException("Não é possível adicionar horas negativas em uma tarefa");
        }
        if (this.isConcluida) {
            throw new IllegalStateException("Não é possível adicionar horas em uma tarefa já concluída");
        }
        this.duracao = this.duracao + horas;
    }

    public void removerHoras(int horas) {
        if (horas < 0) {
            throw new IllegalArgumentException("Não é possível remover horas negativas em uma tarefa");
        }
        if (this.isConcluida) {
            throw new IllegalStateException("Não é possível remover horas em uma tarefa já concluída");
        }
        this.duracao = this.duracao - horas;
    }

    public String toStringReduzido() {
        return this.nome + " - " + this.id + "\n";
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.nome + " - " + this.id + "\n");
        builder.append("- " + this.atividade.getNome() + "\n");
        builder.append(String.join(", ", this.getHabilidades()) + "\n");
        builder.append("(" + this.duracao + " hora(s) executada(s))\n");
        builder.append("===" + "\n");
        builder.append("Equipe" + "\n");
        for (Pessoa pessoa : this.pessoasResponsaveis) {
            builder.append(pessoa.toStringReduzido() + "\n");
        }

        return builder.toString();
    }


}
