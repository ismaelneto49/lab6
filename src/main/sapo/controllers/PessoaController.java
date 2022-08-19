package sapo.controllers;

import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;

import sapo.entities.Comentario;
import sapo.entities.Pessoa;

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
        String[] pessoasString = new String[pessoas.size()];

        for (int i = 0; i < pessoasString.length; i++) {
            pessoasString[i] = pessoas.get(i).toString();
        }

        return pessoasString;
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
