package sapo.services;

import sapo.entities.Pessoa;
import sapo.repositories.PessoaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PessoaService {

    private PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public void cadastrarPessoa(String cpf, String nome, String[] habilidades) {
        this.validarCampoVazio(cpf, "cpf");
        this.validarCampoVazio(nome, "nome");
        Pessoa p = new Pessoa(cpf, nome, habilidades);
        this.pessoaRepository.cadastrarPessoa(cpf, p);
    }

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

    public String[] buscar(String[] termos) {
        String termo1 = termos[0];
        String termo2 = termos[1];
        List<Pessoa> busca = this.pessoaRepository.buscar(termo1, termo2);
        String[] resultado = busca
                .stream()
                .map(pessoa -> pessoa.toString())
                .collect(Collectors.toList())
                .toArray(new String[]{});
        return resultado;
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
