package sapo;

import sapo.controllers.PessoaController;

import sapo.entities.Pessoa;

import java.util.HashMap;

public class Facade {

    /*
     * TODO
     * <ul>
     * <li>Add all controllers</li>
     * <li>Add each of its methods</li>
     * </ul>
     */
    private PessoaController pessoaController;

    public Facade() {
        /*
         * TODO
         * initialize all controllers
         */
        this.pessoaController = new PessoaController(new HashMap<>());
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

}