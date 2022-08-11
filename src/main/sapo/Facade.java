package sapo;

import sapo.controllers.TarefaController;

import java.util.HashMap;

public class Facade {

    private AtividadeController atividadeController;
    private TarefaController tarefaController;

    public Facade() {
        this.atividadeController = new AtividadeController(new HashMap<>());
        this.tarefaController = new TarefaController(new HashMap<>(), this.atividadeController);
    }
    public String cadastrarTarefa(String idAtividade, String nome, String[] habilidadesRecomendadas) {
        return this.tarefaController.cadastrarTarefa(idAtividade, nome, habilidadesRecomendadas);
    }

    public void alterarNomeTarefa(String idTarefa, String novoNome) {
        this.tarefaController.alterarNomeTarefa(idTarefa, novoNome);
    }

    public void alterarHabilidadesTarefa(String idTarefa, String[] habilidades) {
        this.tarefaController.alterarHabilidadesTarefa(idTarefa, habilidades);
    }

    public void adicionarHorasTarefa(String idTarefa, int horas) {
        this.tarefaController.adicionarHorasTarefa(idTarefa, horas);
    }

    public void removerHorasTarefa(String idTarefa, int horas) {
        this.tarefaController.removerHorasTarefa(idTarefa, horas);
    }

    public String exibirTarefa(String idTarefa) {
        return this.tarefaController.exibirTarefa(idTarefa);
    }
    public void associarPessoaTarefa(String cpf, String idTarefa) {
        this.tarefaController.associarPessoaTarefa(cpf, idTarefa);
    }

    public void removerPessoaTarefa(String cpf, String idTarefa) {
        this.tarefaController.removerPessoaTarefa(cpf, idTarefa);
    }


    }