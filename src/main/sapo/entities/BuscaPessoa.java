package sapo.entities;

import sapo.interfaces.Busca;
import sapo.repositories.PessoaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BuscaPessoa implements Busca {

    private PessoaRepository pessoaRepository;
    private String[] consulta;

    public BuscaPessoa(PessoaRepository pessoaRepository, String[] consulta) {
        this.pessoaRepository = pessoaRepository;
        this.consulta = consulta;
    }

    @Override
    public String[] buscar(String[] termos) {
        String termo1 = termos[0].toLowerCase();
        String termo2 = termos[1].toLowerCase();
        List<Pessoa> busca = this.pessoaRepository.buscar(termo1, termo2);
        String[] resultado = busca
                .stream()
                .map(pessoa -> pessoa.toString())
                .collect(Collectors.toList())
                .toArray(new String[]{});
        return resultado;
    }

    @Override
    public String[] getConsulta() {
        return this.consulta;
    }
}
