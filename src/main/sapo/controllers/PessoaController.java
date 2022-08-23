package sapo.controllers;

import sapo.entities.Pessoa;
import sapo.repositories.PessoaRepository;

import java.util.NoSuchElementException;

public class PessoaController {

    private PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public void cadastrarPessoa(String cpf, String nome, String[] habilidades) {
        this.validarCampoVazio(cpf, "cpf");
        this.validarCampoVazio(nome, "nome");
        Pessoa p = new Pessoa(cpf, nome, habilidades);
        this.pessoaRepository.save(cpf, p);
    }

    public void cadastrarAluno(String cpf, String nome, String matricula, int periodo, String[] habilidades) {
        this.validarCampoVazio(cpf, "cpf");
        this.validarCampoVazio(nome, "nome");
        this.validarCampoVazio(matricula, "matricula");
        this.pessoaRepository.cadastrarAluno(cpf, nome, matricula, periodo, habilidades);    }

    public void cadastrarProfessor(String cpf, String nome, String siape, String[] disciplinas, String[] habilidades) {
        this.validarCampoVazio(cpf, "cpf");
        this.validarCampoVazio(nome, "nome");
        this.validarCampoVazio(siape, "siape");

        this.pessoaRepository.cadastrarProfessor(cpf, nome, siape, disciplinas, habilidades);    }

    public Pessoa recuperarPessoa(String cpf) {
        this.validarContemCpf(cpf);
        return this.pessoaRepository.getPessoaByCpf(cpf);
    }

    public String exibirPessoa(String cpf) {
        this.validarContemCpf(cpf);
        return this.pessoaRepository.exibirPessoa(cpf);
    }

    public void alterarNomePessoa(String cpf, String nome) {
        this.validarContemCpf(cpf);
        this.validarCampoVazio(nome, "nome");
        this.pessoaRepository.alterarNomePessoa(cpf, nome);
    }

    public void alterarHabilidadesPessoa(String cpf, String[] habilidades) {
        this.validarContemCpf(cpf);
        this.pessoaRepository.alterarHabilidadesPessoa(cpf, habilidades);
    }

    public void definirFuncaoAluno(String cpf, String matricula, int periodo) {
        this.validarContemCpf(cpf);
        this.pessoaRepository.definirFuncaoAluno(cpf, matricula, periodo);    }

    public void definirFuncaoProfessor(String cpf, String siape, String[] disciplinas) {
        this.validarContemCpf(cpf);
        this.pessoaRepository.definirFuncaoProfessor(cpf, siape, disciplinas);    }

    public void removerFuncao(String cpf) {
        this.validarContemCpf(cpf);
        this.pessoaRepository.removerFuncao(cpf);    }

    public int pegarNivel(String cpf) {
        this.validarContemCpf(cpf);
        return this.pessoaRepository.getNivel(cpf);    }

    public String[] listarPessoas() {
        return this.pessoaRepository.listarPessoas();
    }

    public void removerPessoa(String cpf) {
        this.validarContemCpf(cpf);
        this.pessoaRepository.removerPessoa(cpf);
    }

    public void adicionarComentario(String destinatarioCpf, String comentario, String autorCpf) {
        this.validarContemCpf(destinatarioCpf);
        this.validarContemCpf(autorCpf);
        this.validarCampoVazio(comentario, "comentario");
        this.pessoaRepository.adicionarComentario(destinatarioCpf, comentario, autorCpf);
    }

    public String listarComentarios(String cpf) {
        this.validarContemCpf(cpf);
        return this.pessoaRepository.listarComentarios(cpf);
    }

    private void validarContemCpf(String cpf) {
        if (!this.pessoaRepository.contains(cpf)) {
            throw new NoSuchElementException("CPF fornecido não pertence a nenhuma Pessoa");
        }
    }

    private void validarCampoVazio(String field, String fieldName) {
        if (field == null || field.isBlank()) {
            String message = "Campo " + fieldName.trim() + " não pode ser vazio";
            throw new IllegalArgumentException(message);
        }
    }
}
