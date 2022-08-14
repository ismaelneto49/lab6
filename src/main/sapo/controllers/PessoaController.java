package sapo.controllers;

import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;

import sapo.entities.Comentario;
import sapo.entities.Pessoa;
import sapo.services.PessoaService;

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
