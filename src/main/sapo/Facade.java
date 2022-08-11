package sapo;

import sapo.controllers.AtividadeController;
import sapo.controllers.TarefaController;
import sapo.controllers.PessoaController;
import sapo.entities.Atividade;

import sapo.entities.Pessoa;

import java.util.HashMap;
import java.util.Optional;

public class Facade {

    private AtividadeController atividadeController;
    private TarefaController tarefaController;
    private PessoaController pessoaController;

    public Facade() {
        this.atividadeController = new AtividadeController(new HashMap<>());
        this.pessoaController = new PessoaController(new HashMap<>());
        this.tarefaController = new TarefaController(new HashMap<>(), this.atividadeController, this.pessoaController);

    }

    public void cadastrarPessoa(String cpf, String nome, String[] habilidades) {
        this.pessoaController.cadastrarPessoa(cpf, nome, habilidades);
    }

    public Pessoa recuperarPessoa(String cpf) {
        return this.pessoaController.recuperarPessoa(cpf);
    }

    public String exibirPessoa(String cpf) {
        return this.pessoaController.exibirPessoa(cpf);
    }

    public void alterarNomePessoa(String cpf, String nome) {
        this.pessoaController.alterarNomePessoa(cpf, nome);
    }

    public void alterarHabilidadesPessoa(String cpf, String[] habilidades) {
        this.pessoaController.alterarHabilidadesPessoa(cpf, habilidades);
    }

    public void removerPessoa(String cpf) {
        this.pessoaController.removerPessoa(cpf);
    }

    public void adicionarComentario(String destinatarioCpf, String comentario, String autorCpf) {
        this.pessoaController.adicionarComentario(destinatarioCpf, comentario, autorCpf);
    }

    public String listarComentarios(String cpf) {
        return this.pessoaController.listarComentarios(cpf);
    }

    public String cadastrarAtividade(String nome, String descricao, String cpf) {
        return this.atividadeController.cadastrarAtividade(nome, descricao, cpf);
    }

    public Optional<Atividade> recuperarAtividade(String id){
        return this.atividadeController.recuperarAtividade(id);
    }

    public void encerrarAtividade(String id) {
        this.atividadeController.encerrarAtividade(id);
    }

    public void desativarAtividade(String id) {
        this.atividadeController.desativarAtividade(id);
    }
    public void reabrirAtividade(String id) {
        this.atividadeController.reabrirAtividade(id);
    }

    public String exibirAtividade(String id) {
        return this.atividadeController.exibirAtividade(id);
    }

    public void alterarDescricaoAtividade(String id, String descricao) {
        this.atividadeController.alterarDescricaoAtividade(id, descricao);
    }

    public void alterarResponsavelAtividade(String id, String cpf) {
        this.atividadeController.alterarResponsavelAtividade(id, cpf);
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