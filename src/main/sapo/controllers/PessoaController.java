package sapo.controllers;

import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;

import sapo.entities.Comentario;
import sapo.entities.Pessoa;

public class PessoaController {

    private Map<String, Pessoa> pessoas;

    public PessoaController(Map<String, Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public void cadastrarPessoa(String cpf, String nome, String[] habilidades) {
        this.validarCampoVazio(cpf, "cpf");
        this.validarCampoVazio(nome, "nome");

        Pessoa p = new Pessoa(cpf, nome, habilidades);
        this.pessoas.put(cpf, p);
    }


    public Pessoa recuperarPessoa(String cpf) {
        this.validarContemCpf(cpf);

        return this.pessoas.get(cpf);
    }

    public String exibirPessoa(String cpf) {
        this.validarContemCpf(cpf);

        return this.recuperarPessoa(cpf).toString();
    }

    public void alterarNomePessoa(String cpf, String nome) {
        this.validarContemCpf(cpf);
        this.validarCampoVazio(nome, "nome");

        Pessoa p = this.recuperarPessoa(cpf);
        p.setNome(nome);
    }

    public void alterarHabilidadesPessoa(String cpf, String[] habilidades) {
        this.validarContemCpf(cpf);

        this.recuperarPessoa(cpf).setHabilidades(habilidades);
    }

    public void removerPessoa(String cpf) {
        this.validarContemCpf(cpf);

        this.pessoas.remove(cpf);
    }

    public void adicionarComentario(String destinatarioCpf, String comentario, String autorCpf) {
        this.validarContemCpf(destinatarioCpf);
        this.validarContemCpf(autorCpf);
        this.validarCampoVazio(comentario, "comentario");

        Pessoa autor = this.recuperarPessoa(autorCpf);
        Pessoa destinatario = this.recuperarPessoa(destinatarioCpf);
        Comentario c = new Comentario(comentario, LocalDate.now(), autor, destinatario);
        destinatario.adicionarComentario(c);
    }

    public String listarComentarios(String cpf) {
        this.validarContemCpf(cpf);

        return this.recuperarPessoa(cpf).listarComentarios();
    }

    private void validarContemCpf(String cpf) {
        if (!this.pessoas.keySet().contains(cpf)) {
            throw new NoSuchElementException("Given cpf does not belong to any Pessoa!");
        }
    }

    private void validarCampoVazio(String field, String fieldName) {
        if (field == null || field.isBlank()) {
            String message = "Field " + fieldName.trim() + " cannot be empty!";
            throw new IllegalArgumentException(message);
        }
    }
}
