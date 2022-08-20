package sapo.entities;

import sapo.interfaces.Busca;

public class BuscaTarefa implements Busca {

    public String[] consulta;

    public BuscaTarefa(String[] consulta) {
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
