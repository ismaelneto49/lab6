package sapo.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Atividade {

    private List<Tarefa> tarefas;
    private List<TarefaGerencial> tarefasGerenciais;
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
        this.tarefas = new ArrayList<>();
        this.tarefasGerenciais = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public String getId() {
        return this.id;
    }

    public void setDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    public void setResponsavel(Pessoa novoResponsavel) {
        this.responsavel = novoResponsavel;
    }

    public void cadastrarTarefa(Tarefa tarefa) {
        if (this.status == AtividadeStatus.ENCERRADA || this.status == AtividadeStatus.DESATIVADA) {
            throw new IllegalStateException("A atividade não pode receber novas tarefas pois seu status atual é " + this.status);
        }
        this.tarefas.add(tarefa);
    }

    public void encerrar() {
        if (this.status == AtividadeStatus.ENCERRADA) {
            throw new IllegalStateException("A atividade " + this.id + " já está encerrada.");
        }
        if (this.status == AtividadeStatus.DESATIVADA) {
            throw new IllegalStateException("A atividade " + this.id + " não pode ser encerrada pois está desativada.");
        }
        if (this.getQuantidadeTarefasPendentes() > 0) {
            throw new IllegalStateException("A atividade " + this.id + " possui tarefas pendentes.");
        }
        this.status = AtividadeStatus.ENCERRADA;
    }

    public void desativar() {
        if (this.status == AtividadeStatus.DESATIVADA) {
            throw new IllegalStateException("A atividade " + this.id + " já está desativada.");
        }
        if (this.getQuantidadeTarefasPendentes() > 0) {
            throw new IllegalStateException("A atividade " + this.id + " possui tarefas pendentes.");
        }
        this.status = AtividadeStatus.DESATIVADA;
    }

    public void reabrir() {
        if (this.status == AtividadeStatus.ATIVADA) {
            throw new IllegalStateException("A atividade " + this.id + " já está aberta");
        }
        this.status = AtividadeStatus.ATIVADA;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.id + ": " + this.nome + "\n");
        if (Objects.nonNull(this.responsavel)) {
            builder.append("Responsável: " + this.responsavel.toStringReduzido() + "\n");
        }
        builder.append("===" + "\n");
        builder.append(this.descricao + "\n");
        builder.append("===" + "\n");
        builder.append("Tarefas: " + this.getQuantidadeTarefasConcluidas() + "/" + this.getQuantidadeTarefasTotal() + "\n");

        for (int i = this.tarefas.size() - 1; i >= Math.max(this.tarefas.size() - 4, 0); i--) {
            builder.append("- " + this.tarefas.get(i).toStringReduzido());
        }

        return builder.toString();
    }

    private int getQuantidadeTarefasConcluidas() {
        int quantidadeTarefasConcluidas = 0;
        for (Tarefa tarefa : this.tarefas) {
            if (tarefa.getIsConcluida()) {
                quantidadeTarefasConcluidas += 1;
            }
        }
        return quantidadeTarefasConcluidas;
    }

    private int getQuantidadeTarefasPendentes() {
        return this.getQuantidadeTarefasTotal() - this.getQuantidadeTarefasConcluidas();
    }

    private int getQuantidadeTarefasTotal() {
        return this.tarefas.size();
    }

    public void cadastrarTarefaGerencial(TarefaGerencial tarefa) {
        if (this.status == AtividadeStatus.ENCERRADA || this.status == AtividadeStatus.DESATIVADA) {
            throw new IllegalStateException("A atividade não pode receber novas tarefas pois seu status atual é " + this.status);
        }
        this.tarefasGerenciais.add(tarefa);
    }
}

