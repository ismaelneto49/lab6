package sapo;

import sapo.controllers.AtividadeController;
import sapo.controllers.TarefaController;
import sapo.controllers.PessoaController;
import sapo.entities.Atividade;

import sapo.entities.Pessoa;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Facade {

    private AtividadeController atividadeController;
    private TarefaController tarefaController;
    private PessoaController pessoaController;

    public Facade() {
        this.pessoaController = new PessoaController(new HashMap<>());
        this.atividadeController = new AtividadeController(new HashMap<>(), this.pessoaController);
        this.tarefaController = new TarefaController(new HashMap<>(), new HashMap<>(), this.atividadeController, this.pessoaController);
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

    public void cadastrarAluno(String cpf, String nome, String matricula, int periodo, String[] habilidades) {
        this.pessoaController.cadastrarAluno(cpf, nome, matricula, periodo, habilidades);
    }

    public void cadastrarProfessor(String cpf, String nome, String siape, String[] disciplinas, String[] habilidades) {
        this.pessoaController.cadastrarProfessor(cpf, nome, siape, disciplinas, habilidades);
    }

    public void definirFuncaoProfessor(String cpf, String siape, String[] disciplinas) {
        this.pessoaController.definirFuncaoProfessor(cpf, siape, disciplinas);
    }

    public void definirFuncaoAluno(String cpf, String matricula, int periodo) {
        this.pessoaController.definirFuncaoAluno(cpf, matricula, periodo);
    }

    public void removerFuncao(String cpf) {
        this.pessoaController.removerFuncao(cpf);
    }

    public int pegarNivel(String cpf) {
        return this.pessoaController.pegarNivel(cpf);
    }

    public String[] listarPessoas() {
        return this.pessoaController.listarPessoas();
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

    public Optional<Atividade> recuperarAtividade(String id) {
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

    public void concluirTarefa(String idTarefa) {
        this.tarefaController.concluirTarefa(idTarefa);
    }

    public void removerTarefa(String idTarefa) {
        throw new NoSuchElementException("Not yet implemented");
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

    public String cadastrarTarefaGerencial(String atividadeId, String nome, String[] habilidades, String[] idTarefas) {
        return this.tarefaController.cadastrarTarefaGerencial(atividadeId, nome, habilidades, idTarefas);
    }

    public void adicionarNaTarefaGerencial(String idTarefaGerencial, String idTarefa) {
        this.tarefaController.adicionarNaTarefaGerencial(idTarefaGerencial, idTarefa);
    }

    public void removerDaTarefaGerencial(String idTarefaGerencial, String idTarefa) {
        this.tarefaController.removerDaTarefaGerencial(idTarefaGerencial, idTarefa);
    }

    public int contarTodasTarefasNaTarefaGerencial(String idTarefaGerencial) {
        return this.tarefaController.contarTodasTarefasNaTarefaGerencial(idTarefaGerencial);
    }

}