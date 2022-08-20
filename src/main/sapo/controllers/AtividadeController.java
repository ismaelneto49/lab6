package sapo.controllers;

import sapo.entities.Atividade;
import sapo.entities.Pessoa;
import sapo.repositories.AtividadeRepository;
import sapo.repositories.PessoaRepository;

import java.util.*;

public class AtividadeController {
    private PessoaRepository pessoaRepository;
    private AtividadeRepository atividadeRepository;

    public AtividadeController(PessoaRepository pessoaRepository, AtividadeRepository atividadeRepository) {
        this.pessoaRepository = pessoaRepository;
        this.atividadeRepository = atividadeRepository;
    }

    public String cadastrarAtividade(String nome, String descricao, String cpf) {
        this.validarParametro(nome, "nome");
        this.validarParametro(descricao, "descrição");
        this.validarParametro(cpf, "CPF");
        this.validarContemCpf(cpf);
        Pessoa responsavel = this.pessoaRepository.getPessoaByCpf(cpf);
        return this.atividadeRepository.cadastrarAtividade(nome, descricao, responsavel);
    }

    public Optional<Atividade> recuperarAtividade(String id) {
        if (this.atividadeRepository.contains(id)) {
            return Optional.of(this.atividadeRepository.getAtividadeById(id));
        }
        return Optional.empty();
    }

    public void encerrarAtividade(String id) {
        this.validarIdAtividade(id);
        this.atividadeRepository.encerrarAtividade(id);
    }

    public void desativarAtividade(String id) {
        this.validarIdAtividade(id);
        this.atividadeRepository.desativarAtividade(id);
    }

    public void reabrirAtividade(String id) {
        this.validarIdAtividade(id);
        this.atividadeRepository.reabrirAtividade(id);
    }

    public String exibirAtividade(String id) {
        this.validarIdAtividade(id);
        return this.atividadeRepository.exibirAtividade(id);
    }

    public void alterarDescricaoAtividade(String id, String descricao) {
        this.validarParametro(descricao, "descrição");
        this.validarIdAtividade(id);
        this.atividadeRepository.alterarDescricaoAtividade(id, descricao);
    }

    public void alterarResponsavelAtividade(String id, String cpf) {
        this.validarParametro(cpf, "cpf");
        this.validarIdAtividade(id);
        this.validarContemCpf(cpf);
        Pessoa novoResponsavel = this.pessoaRepository.getPessoaByCpf(cpf);
        this.atividadeRepository.alterarResponsavelAtividade(id, novoResponsavel);
    }

    private void validarParametro(String parametro, String nomeParametro) {
        if (Objects.isNull(parametro) || parametro.isBlank()) {
            throw new IllegalArgumentException("Campo " + nomeParametro + " não pode ser nulo ou vazio.");
        }
    }

    private void validarContemCpf(String cpf) {
        if (!this.pessoaRepository.contains(cpf)) {
            throw new NoSuchElementException("CPF fornecido não pertence a nenhuma Pessoa");
        }
    }

    private void validarIdAtividade(String id) {
        if (!this.atividadeRepository.contains(id)) {
            throw new NoSuchElementException("ID fornecido não pertence a nenhuma Atividade");
        }
    }
}
