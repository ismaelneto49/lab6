package sapo.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public void cadastrarAluno(String cpf, String nome, String matricula, int periodo, String[] habilidades) {
        this.validarCampoVazio(cpf, "cpf");
        this.validarCampoVazio(nome, "nome");
        this.validarCampoVazio(matricula, "matricula");

        Pessoa p = new Pessoa(cpf, nome, habilidades);
        p.setFuncaoAluno(matricula, periodo);
        this.pessoas.put(cpf, p);

    }

    public void cadastrarProfessor(String cpf, String nome, String siape, String[] disciplinas, String[] habilidades) {
        this.validarCampoVazio(cpf, "cpf");
        this.validarCampoVazio(nome, "nome");
        this.validarCampoVazio(siape, "siape");

        Pessoa p = new Pessoa(cpf, nome, habilidades);
        p.setFuncaoProfessor(siape, disciplinas);
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

    public void definirFuncaoAluno(String cpf, String matricula, int periodo) {
        this.validarContemCpf(cpf);
        this.recuperarPessoa(cpf).setFuncaoAluno(matricula, periodo);
    }

    public void definirFuncaoProfessor(String cpf, String siape, String[] disciplinas) {
        this.validarContemCpf(cpf);
        this.recuperarPessoa(cpf).setFuncaoProfessor(siape, disciplinas);
    }

    public void removerFuncao(String cpf) {
        this.validarContemCpf(cpf);
        this.recuperarPessoa(cpf).removerFuncao();
    }

    public int pegarNivel(String cpf) {
        this.validarContemCpf(cpf);
        Pessoa pessoa = this.recuperarPessoa(cpf);
        return pessoa.getNivel();
    }

    public String[] listarPessoas() {
        List<Pessoa> pessoas = new ArrayList<>(this.pessoas.values());
        return new String[1];

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
