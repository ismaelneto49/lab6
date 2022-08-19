package sapo.controllers;

import sapo.entities.Atividade;
import sapo.entities.Pessoa;
import sapo.entities.Tarefa;
import sapo.entities.TarefaGerencial;

import java.util.*;

public class TarefaController {

    private Map<Atividade, Map<String, Tarefa>> tarefas;
    private Map<Atividade, Map<String, TarefaGerencial>> tarefasGerenciais;
    private AtividadeController atividadeController;
    private PessoaController pessoaController;

    public TarefaController(Map<Atividade, Map<String, Tarefa>> tarefas, AtividadeController atividadeController, PessoaController pessoaController) {
        this.tarefas = tarefas;
        this.atividadeController = atividadeController;
        this.pessoaController = pessoaController;
    }

    public String cadastrarTarefa(String atividadeId, String nome, String[] habilidades) {
        Atividade atividade = this.atividadeController.validarIdAtividade(atividadeId);
        this.validarParametro(nome, "nome");
        int numeroNovaTarefa = this.calcularQuantidadeTarefas();
        String idNovaTarefa = atividadeId + "-" + numeroNovaTarefa;
        Tarefa novaTarefa = new Tarefa(idNovaTarefa, nome, 0, atividade, habilidades);
        atividade.cadastrarTarefa(novaTarefa);
        boolean atividadeNaoTemTarefa = Objects.isNull(this.tarefas.get(atividade));

        if (atividadeNaoTemTarefa) {
            this.tarefas.put(atividade, new HashMap<>());
        }
        this.tarefas.get(atividade).put(idNovaTarefa, novaTarefa);

        return idNovaTarefa;
    }

    public void alterarNomeTarefa(String idTarefa, String novoNome) {
        Tarefa tarefa = this.validarIdTarefa(idTarefa);
        this.validarParametro(novoNome, "novo nome");
        tarefa.setNome(novoNome);

    }

    public void alterarHabilidadesTarefa(String idTarefa, String[] habilidades) {
        Tarefa tarefa = this.validarIdTarefa(idTarefa);
        tarefa.setHabilidades(habilidades);
    }

    public void adicionarHorasTarefa(String idTarefa, int horas) {
        Tarefa tarefa = this.validarIdTarefa(idTarefa);
        tarefa.adicionarHoras(horas);
    }

    public void removerHorasTarefa(String idTarefa, int horas) {
        Tarefa tarefa = this.validarIdTarefa(idTarefa);
        tarefa.removerHoras(horas);
    }

    public void concluirTarefa(String idTarefa) {
        Tarefa tarefa = this.validarIdTarefa(idTarefa);
        tarefa.concluirTarefa();
    }

    public String exibirTarefa(String idTarefa) {
        Tarefa tarefa = this.validarIdTarefa(idTarefa);
        return tarefa.toString();
    }

    public void associarPessoaTarefa(String cpf, String idTarefa) {
        Pessoa pessoa = pessoaController.recuperarPessoa(cpf);
        Tarefa tarefa = this.validarIdTarefa(idTarefa);
        tarefa.adicionarPessoaResponsavel(pessoa);
        pessoa.adicionarTarefa(tarefa);
    }

    public void removerPessoaTarefa(String cpf, String idTarefa) {
        Pessoa pessoa = pessoaController.recuperarPessoa(cpf);
        Tarefa tarefa = this.validarIdTarefa(idTarefa);
        tarefa.removerPessoaResponsavel(pessoa);
        pessoa.removerTarefa(tarefa);
    }

    private int calcularQuantidadeTarefas() {
        int quantidadeTarefas = 0;
        for (Map<String, Tarefa> tarefasPorAtividade : this.tarefas.values()) {
            quantidadeTarefas += tarefasPorAtividade.size();
        }
        return quantidadeTarefas;
    }

    private void validarParametro(String parametro, String nomeParametro) {
        if (Objects.isNull(parametro) || parametro.isBlank()) {
            throw new IllegalArgumentException("Campo " + nomeParametro + " não pode ser nulo ou vazio.");
        }
    }

    private Tarefa validarIdTarefa(String id) {
        Optional<Tarefa> optionalTarefa = this.recuperarTarefa(id);
        if (optionalTarefa.isEmpty()) {
            throw new NoSuchElementException("O id passado não pertence a nenhuma atividade");
        }
        return optionalTarefa.get();
    }

    private Optional<Tarefa> recuperarTarefa(String id) {
        for (Map<String, Tarefa> tarefasPorAtividade : this.tarefas.values()) {
            if (Objects.nonNull(tarefasPorAtividade.get(id))) {
                return Optional.of(tarefasPorAtividade.get(id));
            }
        }
        return Optional.empty();
    }

    private Optional<TarefaGerencial> recuperarTarefaGerencial(String id) {
        for (Map<String, TarefaGerencial> tarefasPorAtividade : this.tarefasGerenciais.values()) {
            if (Objects.nonNull(tarefasPorAtividade.get(id))) {
                return Optional.of(tarefasPorAtividade.get(id));
            }
        }
        return Optional.empty();
    }

    public String cadastrarTarefaGerencial(String atividadeId, String nome, String[] habilidades, String[] idTarefas) {
        Atividade atividade = this.atividadeController.validarIdAtividade(atividadeId);
        this.validarParametro(nome, "nome");
        int numeroNovaTarefa = this.calcularQuantidadeTarefas();
        String idNovaTarefa = atividadeId + "-" + numeroNovaTarefa;
        TarefaGerencial novaTarefa = new TarefaGerencial(idNovaTarefa, nome, 0, atividade);
        atividade.cadastrarTarefaGerencial(novaTarefa);
        boolean atividadeNaoTemTarefa = Objects.isNull(this.tarefas.get(atividade));

        if (atividadeNaoTemTarefa) {
            this.tarefasGerenciais.put(atividade, new HashMap<>());
        }

        for (String id : idTarefas) {
            Tarefa t = this.recuperarTarefa(id).get();
            novaTarefa.addSubtarefa(t);
        }

        this.tarefasGerenciais.get(atividade).put(idNovaTarefa, novaTarefa);

        return idNovaTarefa;
    }

    public void adicionarNaTarefaGerencial(String idTarefaGerencial, String idTarefa) {
        Tarefa t = this.recuperarTarefa(idTarefa).get();
        this.recuperarTarefaGerencial(idTarefaGerencial).get().addSubtarefa(t);
    }

    public void removerDaTarefaGerencial(String idTarefaGerencial, String idTarefa) {
        Tarefa t = this.recuperarTarefa(idTarefa).get();
        this.recuperarTarefaGerencial(idTarefaGerencial).get().removerTarefa(t);
    }

    public int contarTodasTarefasNaTarefaGerencial(String idTarefaGerencial) {
        return this.recuperarTarefaGerencial(idTarefaGerencial).get().contarTarefas();
    }
}
