package sapo.repositories;

import sapo.entities.Comentario;
import sapo.entities.Pessoa;

import java.time.LocalDate;
import java.util.Map;

public class PessoaRepository {

    private Map<String, Pessoa> pessoas;

    public PessoaRepository(Map<String, Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public boolean contains(String cpf) {
        return this.pessoas.keySet().contains(cpf);
    }

    public Pessoa[] buscar(String[] termos) {
        return null;
    }

    public void cadastrarPessoa(String cpf, Pessoa p) {
        this.pessoas.put(cpf, p);
    }

    public Pessoa recuperarPessoa(String cpf) {
        return this.pessoas.get(cpf);
    }

    public String exibirPessoa(String cpf) {
        return this.recuperarPessoa(cpf).toString();
    }

    public void alterarNomePessoa(String cpf, String nome) {
        this.recuperarPessoa(cpf).setNome(nome);
    }

    public void alterarHabilidadesPessoa(String cpf, String[] habilidades) {
        this.recuperarPessoa(cpf).setHabilidades(habilidades);
    }

    public void removerPessoa(String cpf) {
        this.pessoas.remove(cpf);
    }

    public void adicionarComentario(String destinatarioCpf, String comentario, String autorCpf) {
        Pessoa autor = this.recuperarPessoa(autorCpf);
        Pessoa destinatario = this.recuperarPessoa(destinatarioCpf);
        Comentario c = new Comentario(comentario, LocalDate.now(), autor, destinatario);
        destinatario.adicionarComentario(c);
    }

    public String listarComentarios(String cpf) {
        return this.recuperarPessoa(cpf).listarComentarios();
    }
}
