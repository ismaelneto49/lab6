package sapo.entities;

import sapo.interfaces.Busca;

public class BuscaAtividade implements Busca {

    private String[] consulta;

    public BuscaAtividade(String[] consulta) {
        this.consulta = consulta;
    }

    @Override
    public String[] buscar(String[] termos) {
        return null;
    }

    @Override
    public String[] getConsulta() {
        return this.consulta;
    }
}
