package sapo.controllers;

import sapo.entities.Pessoa;
import sapo.services.PessoaService;

import java.util.Map;

public class PessoaController {

    private Map<String, Pessoa> pessoas;
    private PessoaService pessoaService;

    public PessoaController(Map<String, Pessoa> pessoas, PessoaService pessoaService) {
        this.pessoas = pessoas;
        this.pessoaService = pessoaService;
    }

    public void cadastrarPessoa(String cpf, String nome, String[] habilidades) {
        this.pessoaService.cadastrarPessoa(cpf, nome, habilidades);
    }

    public void cadastrarAluno(String cpf, String nome, String matricula, int periodo, String[] habilidades) {
        this.pessoaService.cadastrarAluno(cpf, nome, matricula, periodo, habilidades);
    }

    public void cadastrarProfessor(String cpf, String nome, String siape, String[] disciplinas, String[] habilidades) {
        this.pessoaService.cadastrarProfessor(cpf, nome, siape, disciplinas, habilidades);
    }

    public Pessoa recuperarPessoa(String cpf) {
        return this.pessoaService.recuperarPessoa(cpf);
    }

    public String exibirPessoa(String cpf) {
        return this.pessoaService.exibirPessoa(cpf);
    }

    public void alterarNomePessoa(String cpf, String nome) {
        this.pessoaService.alterarNomePessoa(cpf, nome);
    }

    public void alterarHabilidadesPessoa(String cpf, String[] habilidades) {
        this.pessoaService.alterarHabilidadesPessoa(cpf, habilidades);
    }

    public void definirFuncaoAluno(String cpf, String matricula, int periodo) {
        this.pessoaService.definirFuncaoAluno(cpf, matricula, periodo);
    }

    public void definirFuncaoProfessor(String cpf, String siape, String[] disciplinas) {
        this.pessoaService.definirFuncaoProfessor(cpf, siape, disciplinas);
    }

    public void removerFuncao(String cpf) {
        this.pessoaService.removerFuncao(cpf);
    }

    public int pegarNivel(String cpf) {
        return this.pessoaService.pegarNivel(cpf);
    }

    public String[] listarPessoas() {
        return this.pessoaService.listarPessoas();
    }

    public void removerPessoa(String cpf) {
        this.pessoaService.removerPessoa(cpf);
    }

    public void adicionarComentario(String destinatarioCpf, String comentario, String autorCpf) {
        this.pessoaService.adicionarComentario(destinatarioCpf, comentario, autorCpf);
    }

    public String listarComentarios(String cpf) {
        return this.pessoaService.listarComentarios(cpf);
    }
}
