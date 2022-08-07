package sapo.entities;

import java.util.Objects;
import java.util.Set;

public class Atividade {
    private Set<Tarefa> tarefas;
    private Pessoa responsavel;
    private String nome;
    private String descricao;
    private String id;
    private AtividadeStatus status;

    public Atividade(String id, String nome, String descricao, Pessoa responsavel) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.responsavel = responsavel;
    }

    public String getNome() {
        return this.nome;
    }

    public void setDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    public void setResponsavel(Pessoa novoResponsavel) {
        this.responsavel = novoResponsavel;
    }

    public void cadastrarTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
    }

    public void encerrar() {
        this.status = AtividadeStatus.ENCERRADA;
    }

    public void desativar() {
        this.status = AtividadeStatus.DESATIVADA;
    }

    public void reabrir() {
        this.status = AtividadeStatus.ATIVADA;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.id + ":" + this.nome + "\n");
        if (Objects.nonNull(this.responsavel)) {
            builder.append("Responsável: " + "Pessoa.toString(to String for listing)" + "\n");
        }
        builder.append("===" + "\n");
        builder.append(this.descricao + "\n");
        builder.append("===" + "\n");
        builder.append("Tarefas: " + this.getQuantidadeTarefasConcluidas() + "/" + this.getQuantidadeTarefasTotal() + "\n");
        String[] tarefasPendentes = getMaisRecentesTarefasPendentes(3);
        for (int i = 0; i < tarefasPendentes.length; i++) {
            builder.append("- " + tarefasPendentes[i] + "\n");
        }

        return builder.toString();
    }

    private int getQuantidadeTarefasConcluidas() {
        return 5;
    }

    private int getQuantidadeTarefasTotal() {
        return 20;
    }

    private String[] getMaisRecentesTarefasPendentes(int quantidade) {
        String[] tarefasPendentes = new String[quantidade];

        for (int i = 0; i < tarefasPendentes.length; i ++) {
            tarefasPendentes[i] = i + "";
        }

        return tarefasPendentes;
    }

}

