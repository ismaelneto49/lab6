package sapo.entities;

import sapo.interfaces.Busca;

import java.util.ArrayList;
import java.util.List;

public class HistoricoBusca {

    private List<Busca> buscas;

    public HistoricoBusca() {
        this.buscas = new ArrayList<>();
    }

    public void save(Busca busca) {
        this.buscas.add(busca);
    }

    public Busca getBuscaById(int id) {
        return this.buscas.get(id);
    }

}
