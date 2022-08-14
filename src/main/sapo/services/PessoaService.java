package sapo.services;

import sapo.entities.Comentario;
import sapo.entities.Pessoa;
import sapo.repositories.PessoaRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public class PessoaService {

    private PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
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

    public String[] buscar(String[] termos) {
        Pessoa[] resultado = this.pessoaRepository.buscar(termos);
        return null;
    }

    public void cadastrarPessoa(String cpf, String nome, String[] habilidades) {
        this.validarCampoVazio(cpf, "cpf");
        this.validarCampoVazio(nome, "nome");
        Pessoa p = new Pessoa(cpf, nome, habilidades);
        this.pessoaRepository.cadastrarPessoa(cpf, p);
    }

    public Pessoa recuperarPessoa(String cpf) {
        this.validarContemCpf(cpf);
        return this.pessoaRepository.recuperarPessoa(cpf);
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
}
