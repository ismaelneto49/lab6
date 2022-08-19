package sapo.repositories;

import sapo.entities.Comentario;
import sapo.entities.Pessoa;
import sapo.entities.TipoBusca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PessoaRepository {

    private Map<String, Pessoa> pessoas;

    public PessoaRepository(Map<String, Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public boolean contains(String cpf) {
        return this.pessoas.keySet().contains(cpf);
    }

    public void save(String cpf, Pessoa p) {
        this.pessoas.put(cpf, p);
    }

    public Pessoa getPessoaByCpf(String cpf) {
        return this.pessoas.get(cpf);
    }

    public String exibirPessoa(String cpf) {
        return this.getPessoaByCpf(cpf).toString();
    }

    public void alterarNomePessoa(String cpf, String nome) {
        this.getPessoaByCpf(cpf).setNome(nome);
    }

    public void alterarHabilidadesPessoa(String cpf, String[] habilidades) {
        this.getPessoaByCpf(cpf).setHabilidades(habilidades);
    }

    public void removerPessoa(String cpf) {
        this.pessoas.remove(cpf);
    }

    public void adicionarComentario(String destinatarioCpf, String comentario, String autorCpf) {
        Pessoa autor = this.getPessoaByCpf(autorCpf);
        Pessoa destinatario = this.getPessoaByCpf(destinatarioCpf);
        Comentario c = new Comentario(comentario, LocalDate.now(), autor, destinatario);
        destinatario.adicionarComentario(c);
    }

    public String listarComentarios(String cpf) {
        return this.getPessoaByCpf(cpf).listarComentarios();
    }

    public List<Pessoa> buscar(String termo1, String termo2) {
        List<Pessoa> resultado = new ArrayList<>();

        TipoBusca tipoTermo1 = this.classificaTipoTermo(termo1);
        TipoBusca tipoTermo2 = this.classificaTipoTermo(termo2);

        switch (tipoTermo1) {
            case CPF:
                resultado.add(this.getPessoaByCpf(termo1));
                break;
            case NOME:
                resultado.addAll(this.filterPessoasByNome(termo1));
                break;
            case HABILIDADE:
                resultado.addAll(this.filterPessoasByHabilidade(termo1));
                break;
        }

        switch (tipoTermo2) {
            case CPF:
                if (!termo1.equals(termo2)) {
                    resultado.clear();
                }
                break;
            case NOME:
                resultado = this.funnelByNome(resultado, termo2);
                break;
            case HABILIDADE:
                resultado = this.funnelByHabilidade(resultado, termo2);
                break;
        }

        return resultado;
    }

    private TipoBusca classificaTipoTermo(String termo) {
        if (this.ehCpf(termo)) {
            return TipoBusca.CPF;
        }
        if (this.ehNome(termo)) {
            return TipoBusca.NOME;
        }
        return TipoBusca.HABILIDADE;
    }

    private List<Pessoa> filterPessoasByNome(String nome) {
        List<Pessoa> resultado = this.pessoas.values().stream().filter(pessoa -> pessoa.getNome().contains(nome)).collect(Collectors.toList());
        return resultado;
    }

    private List<Pessoa> filterPessoasByHabilidade(String habilidade) {
        List<Pessoa> resultado = this.pessoas.values().stream().filter(pessoa -> pessoa.hasHabilidade(habilidade)).collect(Collectors.toList());
        return resultado;
    }

    private List<Pessoa> funnelByNome(List<Pessoa> pessoas, String nome) {
        List<Pessoa> resultado = pessoas.stream().filter(pessoa -> pessoa.getNome().contains(nome)).collect(Collectors.toList());
        return resultado;
    }

    private List<Pessoa> funnelByHabilidade(List<Pessoa> pessoas, String habilidade) {
        List<Pessoa> resultado = pessoas.stream().filter(pessoa -> pessoa.hasHabilidade(habilidade)).collect(Collectors.toList());
        return resultado;
    }

    private boolean ehCpf(String termo) {
        return this.pessoas.keySet().contains(termo);
    }

    private boolean ehNome(String termo) {
        List<String> nomes = pessoas.values().stream().map(pessoa -> pessoa.getNome()).collect(Collectors.toList());
        return nomes.stream().anyMatch(nome -> nome.contains(termo));
    }

    public void cadastrarAluno(String cpf, String nome, String matricula, int periodo, String[] habilidades) {
        Pessoa p = new Pessoa(cpf, nome, habilidades);
        p.setFuncaoAluno(matricula, periodo);
        this.pessoas.put(cpf, p);
    }

    public void cadastrarProfessor(String cpf, String nome, String siape, String[] disciplinas, String[] habilidades) {
        Pessoa p = new Pessoa(cpf, nome, habilidades);
        p.setFuncaoProfessor(siape, disciplinas);
        this.pessoas.put(cpf, p);
    }

    public void definirFuncaoAluno(String cpf, String matricula, int periodo) {
        this.getPessoaByCpf(cpf).setFuncaoAluno(matricula, periodo);
    }

    public void definirFuncaoProfessor(String cpf, String siape, String[] disciplinas) {
        this.getPessoaByCpf(cpf).setFuncaoProfessor(siape, disciplinas);
    }

    public void removerFuncao(String cpf) {
        this.getPessoaByCpf(cpf).removerFuncao();
    }

    public int getNivel(String cpf) {
        return this.getPessoaByCpf(cpf).getNivel();
    }

    public String[] listarPessoas() {
        List<Pessoa> pessoas = new ArrayList<>(this.pessoas.values());
        String[] pessoasString = new String[pessoas.size()];
        for (int i = 0; i < pessoasString.length; i++) {
            pessoasString[i] = pessoas.get(i).toString();
        }
        return pessoasString;
    }
}
