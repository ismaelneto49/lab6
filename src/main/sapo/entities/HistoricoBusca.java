package sapo.entities;

import sapo.interfaces.Busca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoricoBusca {

    private List<Busca> buscas;

    public HistoricoBusca() {
        this.buscas = new ArrayList<>();
    }

    public void save(Busca busca) {
        this.buscas.add(busca);
    }

    private Busca getBuscaById(int id) {
        return this.buscas.get(id);
    }

    public String[] buscasMaisRecentes(int nBuscas) {
        List<String> resultado = new ArrayList<>();
        if (nBuscas > this.buscas.size()) {
            nBuscas = this.buscas.size();
        }
        for (int i = this.buscas.size(); i > this.buscas.size() - nBuscas; i--) {
            resultado.add(this.concatena(this.getBuscaById(i-1)));
        }
        return resultado.toArray(resultado.toArray(new String[]{}));
    }

    private String concatena(Busca busca) {
        List<String> retorno = new ArrayList<>();
        String tipo = "";
        if (busca instanceof BuscaPessoa) {
            tipo = "PESSOA";
        } else if (busca instanceof BuscaAtividade) {
            tipo = "ATIVIDADE";
        } else if (busca instanceof BuscaTarefa) {
            tipo = "TAREFA";
        } else {
            tipo = "SUGESTÃO";
        }
        retorno.add(tipo);
        retorno.addAll(Arrays.asList(busca.buscar(busca.getConsulta())));
        String resultado = "";
        for (String s : retorno) {
            resultado += s + "_";
        }
        return resultado.trim();
    }

    public String[] exibirBusca(int indexBusca) {
        Busca busca = this.getBuscaById(indexBusca);
        return this.concatena(busca).split("_");
    }
}
